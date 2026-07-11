-- 创建 auth_user 用户表
CREATE TABLE IF NOT EXISTS auth_user (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL DEFAULT 0,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    real_name VARCHAR(50),
    nickname VARCHAR(50),
    avatar VARCHAR(500),
    gender SMALLINT DEFAULT 0,
    birthday DATE,
    status SMALLINT NOT NULL DEFAULT 1,
    lock_time TIMESTAMP,
    failed_count INTEGER NOT NULL DEFAULT 0,
    last_login_time TIMESTAMP,
    last_login_ip VARCHAR(50),
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by BIGINT,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by BIGINT,
    deleted SMALLINT NOT NULL DEFAULT 0,
    account_type SMALLINT DEFAULT 0,
    mfa_enabled SMALLINT DEFAULT 0,
    mfa_secret_key VARCHAR(255),
    login_fail_count INTEGER DEFAULT 0,
    remark VARCHAR(1000)
);

COMMENT ON TABLE auth_user IS '用户表';
COMMENT ON COLUMN auth_user.id IS '用户ID';
COMMENT ON COLUMN auth_user.tenant_id IS '租户ID';
COMMENT ON COLUMN auth_user.username IS '用户名';
COMMENT ON COLUMN auth_user.password IS '密码（BCrypt加密）';
COMMENT ON COLUMN auth_user.phone IS '手机号';
COMMENT ON COLUMN auth_user.email IS '邮箱';
COMMENT ON COLUMN auth_user.real_name IS '真实姓名';
COMMENT ON COLUMN auth_user.nickname IS '昵称';
COMMENT ON COLUMN auth_user.avatar IS '头像URL';
COMMENT ON COLUMN auth_user.gender IS '性别：0-未知 1-男 2-女';
COMMENT ON COLUMN auth_user.birthday IS '生日';
COMMENT ON COLUMN auth_user.status IS '状态：0-禁用 1-启用';
COMMENT ON COLUMN auth_user.lock_time IS '账户锁定时间';
COMMENT ON COLUMN auth_user.failed_count IS '登录失败次数';
COMMENT ON COLUMN auth_user.last_login_time IS '最后登录时间';
COMMENT ON COLUMN auth_user.last_login_ip IS '最后登录IP';
COMMENT ON COLUMN auth_user.create_time IS '创建时间';
COMMENT ON COLUMN auth_user.create_by IS '创建人ID';
COMMENT ON COLUMN auth_user.update_time IS '更新时间';
COMMENT ON COLUMN auth_user.update_by IS '更新人ID';
COMMENT ON COLUMN auth_user.deleted IS '删除标记：0-未删除 1-已删除';
COMMENT ON COLUMN auth_user.account_type IS '账户类型：0-系统用户 1-普通用户';
COMMENT ON COLUMN auth_user.mfa_enabled IS 'MFA是否启用：0-未启用 1-已启用';
COMMENT ON COLUMN auth_user.mfa_secret_key IS 'MFA密钥（TOTP）';
COMMENT ON COLUMN auth_user.login_fail_count IS '登录失败次数';
COMMENT ON COLUMN auth_user.remark IS '备注';

CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_username ON auth_user(username) WHERE deleted = 0;
CREATE INDEX IF NOT EXISTS idx_auth_phone ON auth_user(phone, deleted) WHERE deleted = 0;
CREATE INDEX IF NOT EXISTS idx_auth_email ON auth_user(email, deleted) WHERE deleted = 0;
CREATE INDEX IF NOT EXISTS idx_auth_tenant_id ON auth_user(tenant_id, deleted) WHERE deleted = 0;
CREATE INDEX IF NOT EXISTS idx_auth_create_time ON auth_user(create_time);
CREATE INDEX IF NOT EXISTS idx_auth_status ON auth_user(status, deleted) WHERE deleted = 0;

-- 插入默认管理员（如果不存在密码admin123）
INSERT INTO auth_user (
    tenant_id, username, password, real_name, nickname, status, create_by, update_by
) VALUES (
    0, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', '超级管理员', 1, 0, 0
) ON CONFLICT DO NOTHING;
