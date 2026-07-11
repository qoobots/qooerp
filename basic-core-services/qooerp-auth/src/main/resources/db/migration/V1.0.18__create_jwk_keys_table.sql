-- JWK 密钥表 - 用于持久化 OAuth2 JWT 签名密钥
CREATE TABLE IF NOT EXISTS oauth2_jwk_keys (
    id VARCHAR(36) PRIMARY KEY DEFAULT gen_random_uuid(),
    key_id VARCHAR(100) NOT NULL UNIQUE,
    public_key BYTEA NOT NULL,
    private_key BYTEA NOT NULL,
    algorithm VARCHAR(20) DEFAULT 'RS256',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

COMMENT ON TABLE oauth2_jwk_keys IS 'OAuth2 JWT签名密钥表';
COMMENT ON COLUMN oauth2_jwk_keys.key_id IS '密钥ID';
COMMENT ON COLUMN oauth2_jwk_keys.public_key IS '公钥(DER格式)';
COMMENT ON COLUMN oauth2_jwk_keys.private_key IS '私钥(DER格式)';
COMMENT ON COLUMN oauth2_jwk_keys.algorithm IS '加密算法';
COMMENT ON COLUMN oauth2_jwk_keys.is_active IS '是否激活';
