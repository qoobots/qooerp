package com.qoobot.qooerp.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qoobot.qooerp.auth.dto.OAuth2ClientRegisterRequest;
import com.qoobot.qooerp.auth.dto.OAuth2ClientResponse;
import com.qoobot.qooerp.auth.dto.OAuth2ClientVO;
import com.qoobot.qooerp.auth.mapper.OAuth2RegisteredClientMapper;
import com.qoobot.qooerp.auth.service.OAuth2ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OAuth2客户端管理服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ClientServiceImpl implements OAuth2ClientService {

    private final RegisteredClientRepository registeredClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2RegisteredClientMapper clientMapper;
    private final JdbcTemplate jdbcTemplate;

    @Value("${oauth2.authorization-server.issuer:http://localhost:8001/auth}")
    private String issuer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OAuth2ClientResponse registerClient(OAuth2ClientRegisterRequest request) {
        log.info("注册OAuth2客户端: clientName={}, clientType={}", request.getClientName(), request.getClientType());

        // 生成clientId和clientSecret
        String clientId = generateClientId(request.getClientName());
        String clientSecret = "confidential".equals(request.getClientType())
                ? generateClientSecret()
                : null;

        // 构建RegisteredClient
        RegisteredClient.Builder builder = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret(clientSecret != null ? passwordEncoder.encode(clientSecret) : null)
                .clientAuthenticationMethods(methods ->
                    methods.addAll(getClientAuthenticationMethods(request.getClientType()))
                )
                .authorizationGrantTypes(types ->
                    types.addAll(getAuthorizationGrantTypes(request.getAuthorizationGrantTypes()))
                )
                .redirectUris(uris ->
                    uris.addAll(request.getRedirectUris())
                )
                .scopes(scopes ->
                    scopes.addAll(request.getScopes())
                )
                .clientName(request.getClientName())
                .clientIdIssuedAt(Instant.from(LocalDateTime.now()))
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(request.getRequireProofKey())
                        .requireAuthorizationConsent(request.getRequireAuthorizationConsent())
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofSeconds(request.getAccessTokenTimeToLive()))
                        .refreshTokenTimeToLive(Duration.ofSeconds(request.getRefreshTokenTimeToLive()))
                        .authorizationCodeTimeToLive(Duration.ofSeconds(request.getAuthorizationCodeTimeToLive()))
                        .accessTokenFormat(new OAuth2TokenFormat(request.getAccessTokenFormat()))
                        .reuseRefreshTokens(false)
                        .build());

        // 设置登出后重定向URI
        if (request.getPostLogoutRedirectUris() != null && !request.getPostLogoutRedirectUris().isEmpty()) {
            builder.postLogoutRedirectUris(uris ->
                uris.addAll(request.getPostLogoutRedirectUris())
            );
        }

        RegisteredClient registeredClient = builder.build();

        // 保存到数据库
        registeredClientRepository.save(registeredClient);

        log.info("OAuth2客户端注册成功: clientId={}, clientType={}", clientId, request.getClientType());

        // 返回响应
        return OAuth2ClientResponse.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientName(request.getClientName())
                .clientIdIssuedAt(LocalDateTime.from(registeredClient.getClientIdIssuedAt()))
                .clientType(request.getClientType())
                .authorizationGrantTypes(String.join(" ", request.getAuthorizationGrantTypes()))
                .scopes(String.join(",", request.getScopes()))
                .redirectUris(String.join(",", request.getRedirectUris()))
                .postLogoutRedirectUris(request.getPostLogoutRedirectUris() != null
                        ? String.join(",", request.getPostLogoutRedirectUris())
                        : "")
                .requireProofKey(request.getRequireProofKey())
                .message("client_secret仅显示一次,请妥善保管,泄露后请及时重置")
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteClient(String clientId) {
        log.info("删除OAuth2客户端: clientId={}", clientId);

        RegisteredClient client = registeredClientRepository.findByClientId(clientId);
        if (client == null) {
            throw new RuntimeException("客户端不存在");
        }

        // 通过 JdbcTemplate 删除
        jdbcTemplate.update("DELETE FROM oauth2_registered_client WHERE id = ?", client.getId());

        log.info("OAuth2客户端删除成功: clientId={}", clientId);
    }

    @Override
    public List<OAuth2ClientVO> listClients() {
        List<RegisteredClient> clients = clientMapper.findAll();
        return clients.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public OAuth2ClientVO getClientById(String clientId) {
        RegisteredClient client = registeredClientRepository.findByClientId(clientId);
        if (client == null) {
            throw new RuntimeException("客户端不存在");
        }
        return toVO(client);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String resetClientSecret(String clientId) {
        log.info("重置OAuth2客户端密钥: clientId={}", clientId);

        RegisteredClient client = registeredClientRepository.findByClientId(clientId);
        if (client == null) {
            throw new RuntimeException("客户端不存在");
        }

        // 检查是否是机密客户端
        if (!client.getClientAuthenticationMethods().contains(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                && !client.getClientAuthenticationMethods().contains(ClientAuthenticationMethod.CLIENT_SECRET_POST)) {
            throw new RuntimeException("公开客户端不支持重置密钥");
        }

        // 生成新密钥
        String newSecret = generateClientSecret();
        String encodedSecret = passwordEncoder.encode(newSecret);

        // 更新客户端
        RegisteredClient updatedClient = RegisteredClient.from(client)
                .clientSecret(encodedSecret)
                .build();

        registeredClientRepository.save(updatedClient);

        log.info("OAuth2客户端密钥重置成功: clientId={}", clientId);

        return newSecret;
    }

    /**
     * 生成clientId
     */
    private String generateClientId(String clientName) {
        // 生成短uuid作为clientId
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        return clientName.replaceAll("\\s+", "-").toLowerCase() + "-" + uuid;
    }

    /**
     * 生成clientSecret
     */
    private String generateClientSecret() {
        // 生成强随机密钥
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder secret = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            secret.append(chars.charAt(random.nextInt(chars.length())));
        }
        return secret.toString();
    }

    /**
     * 获取客户端认证方式
     */
    private Set<ClientAuthenticationMethod> getClientAuthenticationMethods(String clientType) {
        Set<ClientAuthenticationMethod> methods = new HashSet<>();
        if ("confidential".equals(clientType)) {
            methods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
            methods.add(ClientAuthenticationMethod.CLIENT_SECRET_POST);
        } else if ("public".equals(clientType)) {
            methods.add(ClientAuthenticationMethod.NONE);
        }
        return methods;
    }

    /**
     * 获取授权类型
     */
    private Set<AuthorizationGrantType> getAuthorizationGrantTypes(List<String> grantTypes) {
        return grantTypes.stream()
                .map(AuthorizationGrantType::new)
                .collect(Collectors.toSet());
    }

    /**
     * 转换为VO
     */
    private OAuth2ClientVO toVO(RegisteredClient client) {
        String authenticationMethods = client.getClientAuthenticationMethods().stream()
                .map(ClientAuthenticationMethod::getValue)
                .collect(Collectors.joining(" "));

        String grantTypes = client.getAuthorizationGrantTypes().stream()
                .map(AuthorizationGrantType::getValue)
                .collect(Collectors.joining(" "));

        String scopes = client.getScopes().stream()
                .collect(Collectors.joining(","));

        String redirectUris = client.getRedirectUris().stream()
                .collect(Collectors.joining(","));

        String postLogoutUris = client.getPostLogoutRedirectUris() != null
                ? client.getPostLogoutRedirectUris().stream()
                        .collect(Collectors.joining(","))
                : "";

        // 脱敏clientSecret
        String maskedSecret = client.getClientSecret() != null
                ? "********"
                : null;

        return OAuth2ClientVO.builder()
                .id(client.getId())
                .clientId(client.getClientId())
                .clientIdIssuedAt(LocalDateTime.from(client.getClientIdIssuedAt()))
                .clientSecret(maskedSecret)
                .clientSecretExpiresAt(LocalDateTime.from(client.getClientSecretExpiresAt()))
                .clientName(client.getClientName())
                .clientAuthenticationMethods(authenticationMethods)
                .authorizationGrantTypes(grantTypes)
                .redirectUris(redirectUris)
                .postLogoutRedirectUris(postLogoutUris)
                .scopes(scopes)
                .clientSettings(client.getClientSettings().toString())
                .tokenSettings(client.getTokenSettings().toString())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
