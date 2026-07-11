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

COMMENT ON TABLE oauth2_registered_client IS 'OAuth2注册客户端表';
COMMENT ON COLUMN oauth2_registered_client.id IS '客户端ID';
COMMENT ON COLUMN oauth2_registered_client.client_id IS '客户端标识';
COMMENT ON COLUMN oauth2_registered_client.client_id_issued_at IS '客户端ID颁发时间';
COMMENT ON COLUMN oauth2_registered_client.client_secret IS '客户端密钥';
COMMENT ON COLUMN oauth2_registered_client.client_secret_expires_at IS '客户端密钥过期时间';
COMMENT ON COLUMN oauth2_registered_client.client_name IS '客户端名称';
COMMENT ON COLUMN oauth2_registered_client.client_authentication_methods IS '客户端认证方式';
COMMENT ON COLUMN oauth2_registered_client.authorization_grant_types IS '授权类型';
COMMENT ON COLUMN oauth2_registered_client.redirect_uris IS '重定向URI列表';
COMMENT ON COLUMN oauth2_registered_client.post_logout_redirect_uris IS '注销后重定向URI列表';
COMMENT ON COLUMN oauth2_registered_client.scopes IS '授权范围';
COMMENT ON COLUMN oauth2_registered_client.client_settings IS '客户端设置（JSON）';
COMMENT ON COLUMN oauth2_registered_client.token_settings IS '令牌设置（JSON）';

CREATE INDEX IF NOT EXISTS idx_oauth2_registered_client_client_id ON oauth2_registered_client(client_id);

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

COMMENT ON TABLE oauth2_authorization IS 'OAuth2授权表';
COMMENT ON COLUMN oauth2_authorization.id IS '授权ID';
COMMENT ON COLUMN oauth2_authorization.registered_client_id IS '注册客户端ID';
COMMENT ON COLUMN oauth2_authorization.principal_name IS '主体名称（用户名）';
COMMENT ON COLUMN oauth2_authorization.authorization_grant_type IS '授权类型';
COMMENT ON COLUMN oauth2_authorization.authorized_scopes IS '授权范围';
COMMENT ON COLUMN oauth2_authorization.attributes IS '属性（JSON）';
COMMENT ON COLUMN oauth2_authorization.access_token_type IS '访问令牌类型';
COMMENT ON COLUMN oauth2_authorization.access_token_value IS '访问令牌值';
COMMENT ON COLUMN oauth2_authorization.access_token_issued_at IS '访问令牌颁发时间';
COMMENT ON COLUMN oauth2_authorization.access_token_expires_at IS '访问令牌过期时间';
COMMENT ON COLUMN oauth2_authorization.access_token_metadata IS '访问令牌元数据（JSON）';
COMMENT ON COLUMN oauth2_authorization.refresh_token_value IS '刷新令牌值';
COMMENT ON COLUMN oauth2_authorization.refresh_token_issued_at IS '刷新令牌颁发时间';
COMMENT ON COLUMN oauth2_authorization.refresh_token_expires_at IS '刷新令牌过期时间';
COMMENT ON COLUMN oauth2_authorization.refresh_token_metadata IS '刷新令牌元数据（JSON）';
COMMENT ON COLUMN oauth2_authorization.oidc_id_token_value IS 'OIDC ID令牌值';
COMMENT ON COLUMN oauth2_authorization.oidc_id_token_issued_at IS 'OIDC ID令牌颁发时间';
COMMENT ON COLUMN oauth2_authorization.oidc_id_token_expires_at IS 'OIDC ID令牌过期时间';
COMMENT ON COLUMN oauth2_authorization.oidc_id_token_metadata IS 'OIDC ID令牌元数据（JSON）';
COMMENT ON COLUMN oauth2_authorization.user_code_value IS '用户代码值';
COMMENT ON COLUMN oauth2_authorization.user_code_issued_at IS '用户代码颁发时间';
COMMENT ON COLUMN oauth2_authorization.user_code_expires_at IS '用户代码过期时间';
COMMENT ON COLUMN oauth2_authorization.user_code_metadata IS '用户代码元数据（JSON）';
COMMENT ON COLUMN oauth2_authorization.device_code_value IS '设备代码值';
COMMENT ON COLUMN oauth2_authorization.device_code_issued_at IS '设备代码颁发时间';
COMMENT ON COLUMN oauth2_authorization.device_code_expires_at IS '设备代码过期时间';
COMMENT ON COLUMN oauth2_authorization.device_code_metadata IS '设备代码元数据（JSON）';
COMMENT ON COLUMN oauth2_authorization.authorization_code_value IS '授权码值';
COMMENT ON COLUMN oauth2_authorization.authorization_code_issued_at IS '授权码颁发时间';
COMMENT ON COLUMN oauth2_authorization.authorization_code_expires_at IS '授权码过期时间';
COMMENT ON COLUMN oauth2_authorization.authorization_code_metadata IS '授权码元数据（JSON）';
COMMENT ON COLUMN oauth2_authorization.authorization_code_redirect_uri IS '授权码重定向URI';

CREATE INDEX IF NOT EXISTS idx_oauth2_authorization_registered_client_id ON oauth2_authorization(registered_client_id);
CREATE INDEX IF NOT EXISTS idx_oauth2_authorization_principal_name ON oauth2_authorization(principal_name);
CREATE INDEX IF NOT EXISTS idx_oauth2_authorization_access_token_value ON oauth2_authorization(access_token_value);
CREATE INDEX IF NOT EXISTS idx_oauth2_authorization_refresh_token_value ON oauth2_authorization(refresh_token_value);
CREATE INDEX IF NOT EXISTS idx_oauth2_authorization_authorization_code_value ON oauth2_authorization(authorization_code_value);
