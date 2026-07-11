-- OAuth2 授权表
-- 用于存储 OAuth2 授权信息（授权码、令牌等）
CREATE TABLE IF NOT EXISTS oauth2_authorization (
    id VARCHAR(36) PRIMARY KEY,
    registered_client_id VARCHAR(36) NOT NULL,
    principal_name VARCHAR(200) NOT NULL,
    authorization_grant_type VARCHAR(100) NOT NULL,
    authorized_scopes TEXT,
    attributes TEXT,
    access_token_type VARCHAR(50),
    access_token_value TEXT,
    access_token_issued_at TIMESTAMP,
    access_token_expires_at TIMESTAMP,
    access_token_metadata TEXT,
    refresh_token_value TEXT,
    refresh_token_issued_at TIMESTAMP,
    refresh_token_expires_at TIMESTAMP,
    refresh_token_metadata TEXT,
    oidc_id_token_value TEXT,
    oidc_id_token_issued_at TIMESTAMP,
    oidc_id_token_expires_at TIMESTAMP,
    oidc_id_token_metadata TEXT,
    user_code_value TEXT,
    user_code_issued_at TIMESTAMP,
    user_code_expires_at TIMESTAMP,
    user_code_metadata TEXT,
    device_code_value TEXT,
    device_code_issued_at TIMESTAMP,
    device_code_expires_at TIMESTAMP,
    device_code_metadata TEXT,
    authorization_code_value TEXT,
    authorization_code_issued_at TIMESTAMP,
    authorization_code_expires_at TIMESTAMP,
    authorization_code_metadata TEXT,
    authorization_code_redirect_uri TEXT,
    CONSTRAINT fk_oauth2_authorization_registered_client FOREIGN KEY (registered_client_id) REFERENCES oauth2_registered_client(id) ON DELETE CASCADE
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_oauth2_authorization_registered_client_id ON oauth2_authorization(registered_client_id);
CREATE INDEX IF NOT EXISTS idx_oauth2_authorization_principal_name ON oauth2_authorization(principal_name);
CREATE INDEX IF NOT EXISTS idx_oauth2_authorization_access_token_value ON oauth2_authorization(access_token_value);
CREATE INDEX IF NOT EXISTS idx_oauth2_authorization_refresh_token_value ON oauth2_authorization(refresh_token_value);
CREATE INDEX IF NOT EXISTS idx_oauth2_authorization_authorization_code_value ON oauth2_authorization(authorization_code_value);
