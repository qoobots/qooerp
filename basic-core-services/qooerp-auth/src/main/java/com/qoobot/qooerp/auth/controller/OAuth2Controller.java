package com.qoobot.qooerp.auth.controller;

import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.qoobot.qooerp.auth.common.response.Result;
import com.qoobot.qooerp.auth.converter.OAuth2Converter;
import com.qoobot.qooerp.auth.dto.TokenResponse;
import com.qoobot.qooerp.auth.entity.AuthUser;
import com.qoobot.qooerp.auth.mapper.AuthUserMapper;
import com.qoobot.qooerp.auth.service.OAuth2Service;
import com.qoobot.qooerp.auth.vo.OpenIdConfigurationVO;
import com.qoobot.qooerp.auth.vo.TokenVO;
import com.qoobot.qooerp.auth.vo.UserInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OAuth2 控制器 - 提供 OAuth2 授权端点
 */
@Slf4j
@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
@Tag(name = "OAuth2 授权", description = "OAuth2.0 + OIDC 授权端点")
public class OAuth2Controller {

    private final RegisteredClientRepository registeredClientRepository;
    private final AuthUserMapper authUserMapper;
    private final OAuth2Service oauth2Service;
    private final JwtDecoder jwtDecoder;
    private final JWKSource<SecurityContext> jwkSource;

    /**
     * OAuth2 授权端点 - 授权码模式
     */
    @GetMapping("/authorize")
    @Operation(summary = "授权端点", description = "OAuth2 授权码模式授权端点")
    public String authorize(
            @RequestParam(OAuth2ParameterNames.RESPONSE_TYPE) String responseType,
            @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
            @RequestParam(OAuth2ParameterNames.REDIRECT_URI) String redirectUri,
            @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
            @RequestParam(OAuth2ParameterNames.STATE) String state,
            @RequestParam(value = OAuth2ParameterNames.USERNAME, required = false) String username) {

        log.info("OAuth2 授权请求: clientId={}, redirectUri={}, scope={}, state={}",
                clientId, redirectUri, scope, state);

        // 验证响应类型
        if (!"code".equals(responseType) && !"token".equals(responseType)) {
            return buildErrorUrl(redirectUri, "unsupported_response_type", state);
        }

        // 验证客户端
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);
        if (registeredClient == null) {
            log.warn("客户端不存在: {}", clientId);
            return buildErrorUrl(redirectUri, "invalid_client", state);
        }

        // 验证 redirect_uri 是否在客户端配置的 redirect_uris 列表中
        // 客户端配置的 redirect_uris 是逗号分隔的多个 URI
        Set<String> redirectUris = registeredClient.getRedirectUris();
        String registeredRedirectUris = redirectUris != null ? String.join(",", redirectUris) : "";
        if (!registeredRedirectUris.contains(redirectUri)) {
            log.warn("无效的 redirect_uri: {}, 客户端配置的合法 URIs: {}", redirectUri, registeredRedirectUris);
            return buildErrorUrl(redirectUri, "invalid_redirect_uri", state);
        }

        // 解析 scopes
        Set<String> scopes = scope != null ?
            Set.of(scope.split(" ")) : Set.of("openid");

        // 生成授权码并存储到数据库
        String authorizationCode = UUID.randomUUID().toString();
        oauth2Service.storeAuthorizationCode(authorizationCode, registeredClient, scopes, redirectUri, username);

        // 构建重定向 URL
        String redirectUrl = String.format("%s?code=%s&state=%s", redirectUri, authorizationCode, state);
        return "redirect:" + redirectUrl;
    }

    private String buildErrorUrl(String redirectUri, String error, String state) {
        return String.format("redirect:%s?error=%s&state=%s", redirectUri, error, state);
    }

    /**
     * OAuth2 令牌端点 - 交换授权码获取令牌
     */
    @PostMapping("/token")
    @Operation(summary = "令牌端点", description = "使用授权码或凭证交换访问令牌")
    public Result<TokenVO> token(
            @RequestParam(OAuth2ParameterNames.GRANT_TYPE) String grantType,
            @RequestParam(value = OAuth2ParameterNames.CODE, required = false) String code,
            @RequestParam(value = OAuth2ParameterNames.REDIRECT_URI, required = false) String redirectUri,
            @RequestParam(value = OAuth2ParameterNames.CLIENT_ID, required = false) String clientId,
            @RequestParam(value = OAuth2ParameterNames.CLIENT_SECRET, required = false) String clientSecret,
            @RequestParam(value = OAuth2ParameterNames.USERNAME, required = false) String username,
            @RequestParam(value = OAuth2ParameterNames.PASSWORD, required = false) String password,
            @RequestParam(value = OAuth2ParameterNames.REFRESH_TOKEN, required = false) String refreshToken) {

        log.info("OAuth2 令牌请求: grantType={}, clientId={}", grantType, clientId);

        // 验证客户端
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);
        if (registeredClient == null) {
            log.warn("客户端不存在: {}", clientId);
            return Result.error("invalid_client");
        }

        // 对于授权码模式,验证 redirect_uri
        if ("authorization_code".equals(grantType)) {
            if (redirectUri == null || redirectUri.isEmpty()) {
                log.warn("授权码模式缺少 redirect_uri 参数");
                return Result.error("invalid_request: 授权码模式需要 redirect_uri 参数");
            }

            // 验证 redirect_uri 是否在客户端配置的 redirect_uris 列表中
            Set<String> redirectUris = registeredClient.getRedirectUris();
            String registeredRedirectUris = redirectUris != null ? String.join(",", redirectUris) : "";
            if (!registeredRedirectUris.contains(redirectUri)) {
                log.warn("无效的 redirect_uri: {}, 客户端配置的合法 URIs: {}", redirectUri, registeredRedirectUris);
                return Result.error("invalid_redirect_uri: 提供的 redirect_uri 不在客户端配置的列表中");
            }
        }

        // 根据 grant_type 处理不同的授权类型
        try {
            Result<TokenVO> result = switch (grantType) {
                case "authorization_code" -> handleAuthorizationCodeGrant(code, redirectUri, registeredClient);
                case "password" -> handlePasswordGrant(username, password, registeredClient);
                case "client_credentials" -> handleClientCredentialsGrant(registeredClient);
                case "refresh_token" -> handleRefreshTokenGrant(refreshToken, registeredClient);
                default -> Result.error("unsupported_grant_type");
            };
            log.info("OAuth2 令牌生成成功: result={}", result);
            return result;
        } catch (Exception e) {
            log.error("OAuth2 令牌生成失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 授权码模式
     */
    private Result<TokenVO> handleAuthorizationCodeGrant(
            String code, String redirectUri, RegisteredClient registeredClient) {
        try {
            com.qoobot.qooerp.auth.dto.TokenResponse response =
                    oauth2Service.handleAuthorizationCodeGrant(code, redirectUri, registeredClient);
            return Result.success(OAuth2Converter.toTokenVO(response));
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 密码模式
     */
    private Result<TokenVO> handlePasswordGrant(
            String username, String password, RegisteredClient registeredClient) {
        try {
            com.qoobot.qooerp.auth.dto.TokenResponse response =
                    oauth2Service.handlePasswordGrant(username, password, registeredClient);
            return Result.success(OAuth2Converter.toTokenVO(response));
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 客户端凭证模式
     */
    private Result<TokenVO> handleClientCredentialsGrant(RegisteredClient registeredClient) {
        try {
            TokenResponse response =
                    oauth2Service.handleClientCredentialsGrant(registeredClient);
            TokenVO tokenVO = OAuth2Converter.toTokenVO(response);
            return Result.success(tokenVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 刷新令牌模式
     */
    private Result<TokenVO> handleRefreshTokenGrant(
            String refreshToken, RegisteredClient registeredClient) {
        try {
            com.qoobot.qooerp.auth.dto.TokenResponse response =
                    oauth2Service.handleRefreshTokenGrant(refreshToken, registeredClient);
            return Result.success(OAuth2Converter.toTokenVO(response));
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * OIDC 发现端点
     */
    @GetMapping("/.well-known/openid-configuration")
    @Operation(summary = "OIDC 发现端点", description = "OpenID Connect 配置信息")
    public Result<OpenIdConfigurationVO> openidConfiguration() {
        OpenIdConfigurationVO config =
                OAuth2Converter.buildDefaultOpenIdConfiguration("http://localhost:8001");
        return Result.success(config);
    }

    /**
     * JWKS 端点 - 公钥发布端点
     */
    @GetMapping("/.well-known/jwks.json")
    @Operation(summary = "JWKS 端点", description = "JSON Web Key Set - 用于验证 JWT 签名")
    public Map<String, Object> jwks() {
        try {
            // 使用 JWKSelector 获取所有 JWK
            JWKSelector jwkSelector = new JWKSelector(new JWKMatcher.Builder().build());
            List<JWK> jwkList = jwkSource.get(jwkSelector, null);

            // 构建符合 RFC 7517 规范的 JWKS 响应
            Map<String, Object> result = new HashMap<>();
            result.put("keys", jwkList);
            return result;
        } catch (Exception e) {
            log.error("获取 JWKS 失败", e);
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("keys", new Object[]{});
            return errorResult;
        }
    }

    /**
     * UserInfo 端点 - 获取用户信息 (OIDC)
     */
    @GetMapping("/userinfo")
    @Operation(summary = "UserInfo 端点", description = "根据 access_token 获取用户信息", security =  { @SecurityRequirement(name = "Authorization") })
    public Result<UserInfoVO> userinfo(@RequestHeader(value = "Authorization", required = false) String authorization) {
        log.info("UserInfo请求 - Authorization header: {}, 长度: {}",
                authorization != null ? authorization.substring(0, Math.min(50, authorization.length())) : "null",
                authorization != null ? authorization.length() : 0);
        
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.warn("Authorization header验证失败 - authorization为空或不以Bearer开头");
            return Result.error("invalid_request: 缺少 Authorization header");
        }

        String token = authorization.replace("Bearer ", "").trim();

        // 检查 token 是否在黑名单中
        if (oauth2Service.isTokenBlacklisted(token)) {
            return Result.error("invalid_token: token 已被撤销");
        }

        try {
            // 验证并解析 JWT
            Jwt jwt = jwtDecoder.decode(token);

            // 从 JWT claims 中获取用户信息
            Long userId = jwt.getClaim("user_id");
            if (userId == null) {
                userId = Long.parseLong(jwt.getSubject());
            }

            // 查询用户详细信息
            AuthUser user = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
                    .eq(AuthUser::getId, userId)
                    .eq(AuthUser::getDeleted, 0)
                    .last("LIMIT 1"));

            if (user == null) {
                return Result.error("invalid_token: 用户不存在");
            }

            // 构建 UserInfo VO
            UserInfoVO userinfo = OAuth2Converter.toUserInfoVO(user, jwt);
            return Result.success(userinfo);
        } catch (Exception e) {
            log.error("解析 JWT token 失败", e);
            return Result.error("invalid_token: " + e.getMessage());
        }
    }

    /**
     * 撤销令牌端点 (RFC 7009)
     */
    @PostMapping("/revoke")
    @Operation(summary = "撤销令牌", description = "撤销 access_token 或 refresh_token")
    public Result<Void> revoke(
            @RequestParam("token") String token,
            @RequestParam(value = "token_type_hint", required = false) String tokenType) {

        log.info("撤销令牌请求: tokenType={}", tokenType);

        if (token == null || token.isEmpty()) {
            return Result.error("invalid_request: 缺少 token 参数");
        }

        try {
            // 根据 token 类型设置不同的过期时间
            long expirySeconds;

            if ("access_token".equals(tokenType)) {
                // access_token 通常有效期较短，设置黑名单时间为 token 的剩余有效期
                expirySeconds = 7200; // 默认 2 小时
            } else if ("refresh_token".equals(tokenType)) {
                // refresh_token 通常有效期较长，设置黑名单时间为 7 天
                expirySeconds = 604800; // 7 天
            } else {
                // 未指定类型，默认设置较长的黑名单时间
                expirySeconds = 604800; // 7 天
            }

            oauth2Service.addToBlacklist(token, expirySeconds);
            log.info("令牌已撤销并加入黑名单: tokenType={}", tokenType);

            return Result.success();
        } catch (Exception e) {
            log.error("撤销令牌失败", e);
            return Result.error("server_error: 撤销令牌失败");
        }
    }

}
