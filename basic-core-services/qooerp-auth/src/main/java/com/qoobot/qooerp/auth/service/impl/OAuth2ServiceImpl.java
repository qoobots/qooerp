package com.qoobot.qooerp.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qoobot.qooerp.auth.common.security.PasswordUtil;
import com.qoobot.qooerp.auth.dto.TokenResponse;
import com.qoobot.qooerp.auth.entity.AuthUser;
import com.qoobot.qooerp.auth.mapper.AuthUserMapper;
import com.qoobot.qooerp.auth.security.OAuth2UserDetailsService;
import com.qoobot.qooerp.auth.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OAuth2 业务服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ServiceImpl implements OAuth2Service {

    private final AuthUserMapper authUserMapper;
    private final OAuth2UserDetailsService userDetailsService;
    private final OAuth2AuthorizationService authorizationService;
    private final JwtEncoder jwtEncoder;

    /**
     * 令牌黑名单 - 用于撤销令牌
     */
    private final Map<String, Instant> tokenBlacklist = new ConcurrentHashMap<>();

    @Override
    public TokenResponse handleAuthorizationCodeGrant(
            String code, String redirectUri, RegisteredClient registeredClient) {

        log.info("处理授权码授权: code={}, redirectUri={}, clientId={}", code, redirectUri, registeredClient.getClientId());

        // 从数据库获取授权码对应的 OAuth2Authorization
        OAuth2Authorization authorization = authorizationService.findByToken(
                code, new OAuth2TokenType(OAuth2ParameterNames.CODE));

        if (authorization == null) {
            log.warn("授权码无效或已过期: code={}", code);
            throw new RuntimeException("invalid_grant: 授权码无效或已过期");
        }

        log.info("找到授权码授权: principalName={}, scopes={}",
                authorization.getPrincipalName(), authorization.getAuthorizedScopes());

        // 从 authorization attributes 中获取用户信息
        String principalName = authorization.getPrincipalName();
        if (principalName == null) {
            log.error("授权码的 principalName 为 null");
            throw new RuntimeException("invalid_grant: 授权码信息不完整");
        }

        AuthUser user = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getUsername, principalName)
                .eq(AuthUser::getDeleted, 0)
                .last("LIMIT 1"));

        if (user == null) {
            log.warn("用户不存在: principalName={}", principalName);
            throw new RuntimeException("invalid_grant: 用户不存在");
        }

        log.info("找到用户: username={}, realName={}", user.getUsername(), user.getRealName());

        // 获取授权范围
        Set<String> scopes = authorization.getAuthorizedScopes();
        if (scopes == null || scopes.isEmpty()) {
            log.warn("授权码的授权范围为空，使用默认 scope");
            scopes = Set.of("openid");
        }

        // 删除已使用的授权码
        authorizationService.remove(authorization);

        // 生成 JWT 令牌
        return generateToken(user, registeredClient, scopes);
    }

    @Override
    public TokenResponse handlePasswordGrant(
            String username, String password, RegisteredClient registeredClient) {

        if (username == null || password == null) {
            throw new RuntimeException("invalid_request: 缺少用户名或密码");
        }

        // 验证用户名密码
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 验证密码
            if (!PasswordUtil.matches(password, userDetails.getPassword())) {
                log.warn("密码验证失败: {}", username);
                throw new RuntimeException("invalid_grant: 用户名或密码错误");
            }

            // 获取用户实体
            AuthUser user = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
                    .eq(AuthUser::getUsername, username)
                    .eq(AuthUser::getDeleted, 0)
                    .last("LIMIT 1"));

            if (user == null) {
                throw new RuntimeException("invalid_grant: 用户不存在");
            }

            Set<String> scopes = Set.of("openid", "profile", "email");
            return generateToken(user, registeredClient, scopes);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.warn("密码认证失败: {}", username, e);
            throw new RuntimeException("invalid_grant: 用户名或密码错误");
        }
    }

    @Override
    public TokenResponse handleClientCredentialsGrant(RegisteredClient registeredClient) {
        // 客户端凭证模式不需要用户，只返回服务间调用令牌
        return generateToken(null, registeredClient, Set.of("read", "write"));
    }

    @Override
    public TokenResponse handleRefreshTokenGrant(
            String refreshToken, RegisteredClient registeredClient) {

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new RuntimeException("invalid_grant: 缺少 refresh_token");
        }

        // 检查 refresh_token 是否在黑名单中
        if (isTokenBlacklisted(refreshToken)) {
            throw new RuntimeException("invalid_grant: refresh_token 已被撤销");
        }

        // 从 refresh_token 获取用户名
        String username = getUsernameFromRefreshToken(refreshToken);
        if (username == null) {
            throw new RuntimeException("invalid_grant: refresh_token 无效");
        }

        // 获取用户
        AuthUser user = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getUsername, username)
                .eq(AuthUser::getDeleted, 0)
                .last("LIMIT 1"));

        if (user == null) {
            throw new RuntimeException("invalid_grant: 用户不存在");
        }

        Set<String> scopes = Set.of("openid", "profile", "email");
        TokenResponse response = generateToken(user, registeredClient, scopes);

        // 将旧的 refresh_token 加入黑名单
        addToBlacklist(refreshToken, 3600);

        return response;
    }

    @Override
    public void storeAuthorizationCode(
            String code,
            RegisteredClient registeredClient,
            Set<String> scopes,
            String redirectUri,
            String username) {

        String principalName;
        if (username != null && !username.isEmpty()) {
            // 使用传入的用户名
            principalName = username;
        } else {
            // 尝试从认证上下文获取用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new IllegalStateException("User not authenticated");
            }
            principalName = authentication.getName();
        }

        // 构建 OAuth2Authorization 对象
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(principalName)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizedScopes(scopes)
                // 存储 redirect_uri 到 OAuth2Authorization 属性
                .attribute("redirect_uri", redirectUri);

        // 创建授权码令牌（5分钟过期）
        // 授权码值通过 builder.token() 传入
        builder.token(new OAuth2AuthorizationCode(code, Instant.now(), Instant.now().plusSeconds(300)));

        OAuth2Authorization authorization = builder.build();
        authorizationService.save(authorization);

        log.debug("授权码已存储: code={}, principalName={}", code, principalName);
    }

    @Override
    public Map<String, Object> buildResponseMap(TokenResponse response) {
        Map<String, Object> result = new HashMap<>();
        result.put("access_token", response.getAccessToken());
        result.put("refresh_token", response.getRefreshToken());
        result.put("token_type", response.getTokenType());
        result.put("expires_in", response.getExpiresIn());
        result.put("scope", response.getScope());
        result.put("id_token", response.getIdToken());
        return result;
    }

    @Override
    public String getUsernameFromRefreshToken(String refreshToken) {
        // 简化实现：实际项目应使用 TokenStore 存储映射关系
        // 这里返回一个默认用户用于测试
        // 实际应该查询数据库或缓存获取 refresh_token 关联的用户
        AuthUser user = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getDeleted, 0)
                .last("LIMIT 1"));
        return user != null ? user.getUsername() : null;
    }

    @Override
    public void addToBlacklist(String token, long expirySeconds) {
        Instant expiryTime = Instant.now().plusSeconds(expirySeconds);
        tokenBlacklist.put(token, expiryTime);
        cleanExpiredBlacklist();
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        Instant expiryTime = tokenBlacklist.get(token);
        if (expiryTime == null) {
            return false;
        }

        // 检查是否已过期
        if (expiryTime.isBefore(Instant.now())) {
            tokenBlacklist.remove(token);
            return false;
        }

        return true;
    }

    /**
     * 清理过期的黑名单项
     */
    private void cleanExpiredBlacklist() {
        Instant now = Instant.now();
        tokenBlacklist.entrySet().removeIf(entry ->
                entry.getValue().isBefore(now)
        );
        log.debug("清理过期的令牌黑名单项完成");
    }

    /**
     * 生成 OAuth2 令牌
     */
    private TokenResponse generateToken(AuthUser user, RegisteredClient registeredClient, Set<String> scopes) {
        long accessTokenExpiresIn = 7200; // 2小时

        log.debug("生成令牌: username={}, clientId={}, scopes={}",
                user != null ? user.getUsername() : null,
                registeredClient.getClientId(),
                scopes);

        try {
            // 构建 JWT Claims
            JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                    .issuer("http://localhost:8001/auth")
                    .subject(user != null ? String.valueOf(user.getId()) : registeredClient.getClientId())
                    .audience(Collections.singletonList(registeredClient.getClientId()))
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plusSeconds(accessTokenExpiresIn))
                    .id(UUID.randomUUID().toString());

            // 添加用户相关 Claims（仅当 user 不为 null 时）
            if (user != null) {
                claimsBuilder.claim("username", user.getUsername())
                        .claim("real_name", user.getRealName())
                        .claim("tenant_id", user.getTenantId());

                // 添加 OIDC Claims（只添加非 null 值）
                if (scopes.contains("openid")) {
                    claimsBuilder.claim("name", user.getRealName());
                    if (user.getEmail() != null) {
                        claimsBuilder.claim("email", user.getEmail());
                    }
                    claimsBuilder.claim("preferred_username", user.getUsername());
                }
            }

            // 添加角色
            Set<GrantedAuthority> authorities = user != null ?
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")) :
                    Collections.emptySet();
            claimsBuilder.claim("authorities", authorities);

            JwtClaimsSet claimsSet = claimsBuilder.build();
            log.debug("JWT Claims 构建完成: {}", claimsSet.getClaims());

            // 生成 JWT
            JwtEncoderParameters parameters = JwtEncoderParameters.from(claimsSet);
            String accessToken = jwtEncoder.encode(parameters).getTokenValue();

            if (accessToken == null || accessToken.isEmpty()) {
                log.error("JwtEncoder 返回了空的 access_token");
                throw new RuntimeException("生成令牌失败: jwtEncoder 返回空值");
            }

            String refreshToken = UUID.randomUUID().toString().replace("-", "");

            log.info("JWT 生成成功: accessToken长度={}, refreshToken={}",
                    accessToken.length(), refreshToken);

            TokenResponse response = TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(accessTokenExpiresIn)
                    .scope(scopes != null ? String.join(" ", scopes) : "")
                    .idToken(accessToken) // OIDC ID Token 与 Access Token 相同
                    .build();

            log.info("TokenResponse 构建成功");
            return response;
        } catch (Exception e) {
            log.error("生成令牌失败", e);
            throw new RuntimeException("生成令牌失败: " + e.getMessage(), e);
        }
    }
}
