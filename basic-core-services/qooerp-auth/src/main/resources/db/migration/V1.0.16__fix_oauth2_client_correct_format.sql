-- 修复 OAuth2 客户端所有字段格式
-- 根据 Spring Security OAuth2 的 JdbcRegisteredClientRepository 源码:
-- - scopes: 使用逗号分隔
-- - client_authentication_methods: 使用空格分隔
-- - authorization_grant_types: 使用空格分隔
-- - redirect_uris: 使用逗号分隔
-- - post_logout_redirect_uris: 使用逗号分隔或空字符串

DELETE FROM oauth2_registered_client WHERE client_id = 'test-client';

INSERT INTO oauth2_registered_client (
    id,
    client_id,
    client_id_issued_at,
    client_secret,
    client_secret_expires_at,
    client_name,
    client_authentication_methods,
    authorization_grant_types,
    redirect_uris,
    post_logout_redirect_uris,
    scopes,
    client_settings,
    token_settings
) VALUES (
    '1a2b3c4d-5e6f-7g8h-9i0j-1k2l3m4n5o6p',
    'test-client',
    CURRENT_TIMESTAMP,
    '$2a$10$NZ7Pw9Z7Z7Z7Z7Z7Z7Z7ZeLJ5Z7Z7Z7Z7Z7Z7Z7Z7Z7Z7Z7Z7Z7Z',
    NULL,
    'Test Client',
    'client_secret_basic client_secret_post',
    'authorization_code refresh_token client_credentials password',
    'http://localhost:3000/callback,http://localhost:3001/callback',
    '',
    'openid,profile,email',
    '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}',
    '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.access-token-time-to-live":"PT7200S","settings.token.refresh-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":"PT604800S","settings.token.authorization-code-time-to-live":"PT300S"}'
);
