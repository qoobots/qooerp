-- 添加 oauth2_authorization 表缺失的字段
-- Spring Security OAuth2 1.3.2 需要这些字段

ALTER TABLE oauth2_authorization ADD COLUMN IF NOT EXISTS state VARCHAR(500);
ALTER TABLE oauth2_authorization ADD COLUMN IF NOT EXISTS access_token_scopes TEXT;
