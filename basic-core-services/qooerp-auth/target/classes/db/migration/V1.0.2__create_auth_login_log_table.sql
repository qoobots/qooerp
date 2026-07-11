-- 创建 auth_login_log 登录日志表
CREATE TABLE IF NOT EXISTS auth_login_log (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL DEFAULT 0,
    user_id BIGINT,
    username VARCHAR(50),
    login_ip VARCHAR(50),
    login_location VARCHAR(100),
    browser VARCHAR(50),
    os VARCHAR(50),
    device VARCHAR(50),
    status SMALLINT NOT NULL DEFAULT 0,
    message VARCHAR(500),
    login_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE auth_login_log IS '登录日志表';
COMMENT ON COLUMN auth_login_log.id IS '日志ID';
COMMENT ON COLUMN auth_login_log.tenant_id IS '租户ID';
COMMENT ON COLUMN auth_login_log.user_id IS '用户ID';
COMMENT ON COLUMN auth_login_log.username IS '用户名';
COMMENT ON COLUMN auth_login_log.login_ip IS '登录IP';
COMMENT ON COLUMN auth_login_log.login_location IS '登录地点';
COMMENT ON COLUMN auth_login_log.browser IS '浏览器类型';
COMMENT ON COLUMN auth_login_log.os IS '操作系统';
COMMENT ON COLUMN auth_login_log.device IS '设备类型';
COMMENT ON COLUMN auth_login_log.status IS '登录状态：0-失败 1-成功';
COMMENT ON COLUMN auth_login_log.message IS '登录消息';
COMMENT ON COLUMN auth_login_log.login_time IS '登录时间';
COMMENT ON COLUMN auth_login_log.create_time IS '创建时间';

CREATE INDEX IF NOT EXISTS idx_auth_login_user_id ON auth_login_log(user_id, login_time);
CREATE INDEX IF NOT EXISTS idx_auth_login_tenant_id ON auth_login_log(tenant_id, login_time);
CREATE INDEX IF NOT EXISTS idx_auth_login_time ON auth_login_log(login_time);
CREATE INDEX IF NOT EXISTS idx_auth_login_status ON auth_login_log(status, login_time);
CREATE INDEX IF NOT EXISTS idx_auth_login_ip ON auth_login_log(login_ip, login_time);
