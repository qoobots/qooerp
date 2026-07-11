package com.qoobot.qooerp.auth.common.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.qoobot.qooerp.auth.entity.OAuth2JwkKey;
import com.qoobot.qooerp.auth.mapper.OAuth2JwkKeyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;


/**
 * OAuth2 Authorization Server 配置
 */
@Configuration
@EnableWebSecurity
public class OAuth2AuthorizationServerConfig {

    private final OAuth2JwkKeyMapper jwkKeyMapper;

    public OAuth2AuthorizationServerConfig(OAuth2JwkKeyMapper jwkKeyMapper) {
        this.jwkKeyMapper = jwkKeyMapper;
    }

    /**
     * OAuth2 Authorization Server 安全过滤器链
     * 只处理标准 OAuth2 协议端点，自定义端点由 SecurityConfig 处理
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http.securityMatcher(
                AntPathRequestMatcher.antMatcher("/.well-known/**")
        );

        http.exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));

        return http.build();
    }

    /**
     * 注册客户端仓库 - JDBC 持久化
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    /**
     * OAuth2 授权服务 - JDBC 持久化
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        JdbcOAuth2AuthorizationService authorizationService =
                new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
        return authorizationService;
    }

    /**
     * JdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * JWT 编码器
     */
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * JWT 解码器
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * JWK 源
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        try {
            KeyPair keyPair = loadOrGenerateRsaKey();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(loadOrGenerateKeyId())
                    .algorithm(JWSAlgorithm.RS256)
                    .build();

            JWKSet jwkSet = new JWKSet(rsaKey);
            return new ImmutableJWKSet<>(jwkSet);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create JWK source", e);
        }
    }

    /**
     * 加载或生成 RSA 密钥对 (持久化到数据库)
     */
    private KeyPair loadOrGenerateRsaKey() {
        // 查询数据库中激活的密钥
        LambdaQueryWrapper<OAuth2JwkKey> wrapper = new LambdaQueryWrapper<OAuth2JwkKey>()
                .eq(OAuth2JwkKey::getIsActive, true)
                .orderByDesc(OAuth2JwkKey::getCreatedAt)
                .last("LIMIT 1");

        OAuth2JwkKey jwkKey = jwkKeyMapper.selectOne(wrapper);

        if (jwkKey != null) {
            try {
                // 加载已保存的密钥
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(jwkKey.getPublicKey());
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(jwkKey.getPrivateKey());

                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
                PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

                System.out.println("Loaded existing RSA key pair from database: " + jwkKey.getKeyId());
                return new KeyPair(publicKey, privateKey);
            } catch (Exception e) {
                System.err.println("Failed to load key from database: " + e.getMessage());
            }
        }

        // 生成新密钥对
        return generateAndSaveRsaKey();
    }

    /**
     * 生成并保存 RSA 密钥对
     */
    private KeyPair generateAndSaveRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            String keyId = UUID.randomUUID().toString();

            // 保存到数据库
            OAuth2JwkKey jwkKey = new OAuth2JwkKey();
            jwkKey.setKeyId(keyId);
            jwkKey.setPublicKey(keyPair.getPublic().getEncoded());
            jwkKey.setPrivateKey(keyPair.getPrivate().getEncoded());
            jwkKey.setAlgorithm("RS256");
            jwkKey.setIsActive(true);
            jwkKeyMapper.insert(jwkKey);

            System.out.println("Generated new RSA key pair and saved to database: " + keyId);
            return keyPair;
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to generate RSA key pair", ex);
        }
    }

    /**
     * 加载或生成密钥 ID (从数据库获取)
     */
    private String loadOrGenerateKeyId() {
        // 查询数据库中激活的密钥
        LambdaQueryWrapper<OAuth2JwkKey> wrapper = new LambdaQueryWrapper<OAuth2JwkKey>()
                .eq(OAuth2JwkKey::getIsActive, true)
                .orderByDesc(OAuth2JwkKey::getCreatedAt)
                .last("LIMIT 1");

        OAuth2JwkKey jwkKey = jwkKeyMapper.selectOne(wrapper);

        if (jwkKey != null) {
            return jwkKey.getKeyId();
        }

        // 如果没有密钥,生成新的密钥对
        KeyPair keyPair = generateAndSaveRsaKey();

        // 再次查询获取 keyId
        OAuth2JwkKey newKey = jwkKeyMapper.selectOne(wrapper);
        return newKey != null ? newKey.getKeyId() : UUID.randomUUID().toString();
    }

    /**
     * 授权服务器设置
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:8001/auth")
                .build();
    }
}
