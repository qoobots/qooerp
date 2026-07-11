-- OAuth2 注册客户端表
-- 用于存储已注册的 OAuth2 客户端信息
CREATE TABLE IF NOT EXISTS oauth2_registered_client (
    id VARCHAR(36) PRIMARY KEY,
    client_id VARCHAR(256) NOT NULL UNIQUE,
    client_id_issued_at TIMESTAMP NOT NULL,
    client_secret VARCHAR(256),
    client_secret_expires_at TIMESTAMP,
    client_name VARCHAR(256) NOT NULL,
    client_authentication_methods TEXT NOT NULL,
    authorization_grant_types TEXT NOT NULL,
    redirect_uris TEXT,
    post_logout_redirect_uris TEXT,
    scopes TEXT NOT NULL,
    client_settings TEXT NOT NULL,
    token_settings TEXT NOT NULL
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_oauth2_registered_client_client_id ON oauth2_registered_client(client_id);
