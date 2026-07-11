-- 创建 auth_device 设备表
CREATE TABLE IF NOT EXISTS auth_device (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    device_id VARCHAR(100) NOT NULL,
    device_name VARCHAR(100),
    device_type VARCHAR(20),
    browser VARCHAR(50),
    os VARCHAR(50),
    last_login_time TIMESTAMP,
    last_login_ip VARCHAR(50),
    status SMALLINT NOT NULL DEFAULT 1,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE auth_device IS '用户设备表';
COMMENT ON COLUMN auth_device.id IS '设备ID';
COMMENT ON COLUMN auth_device.user_id IS '用户ID';
COMMENT ON COLUMN auth_device.device_id IS '设备唯一标识';
COMMENT ON COLUMN auth_device.device_name IS '设备名称';
COMMENT ON COLUMN auth_device.device_type IS '设备类型';
COMMENT ON COLUMN auth_device.browser IS '浏览器';
COMMENT ON COLUMN auth_device.os IS '操作系统';
COMMENT ON COLUMN auth_device.last_login_time IS '最后登录时间';
COMMENT ON COLUMN auth_device.last_login_ip IS '最后登录IP';
COMMENT ON COLUMN auth_device.status IS '状态：0-禁用 1-启用';
COMMENT ON COLUMN auth_device.create_time IS '创建时间';

CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_device_user_device ON auth_device(user_id, device_id);
CREATE INDEX IF NOT EXISTS idx_auth_device_user_id ON auth_device(user_id);
CREATE INDEX IF NOT EXISTS idx_auth_device_last_login_time ON auth_device(last_login_time);

-- 创建 auth_third_party 第三方登录表
CREATE TABLE IF NOT EXISTS auth_third_party (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    platform VARCHAR(20) NOT NULL,
    open_id VARCHAR(100) NOT NULL,
    union_id VARCHAR(100),
    nickname VARCHAR(50),
    avatar VARCHAR(500),
    gender SMALLINT DEFAULT 0,
    bind_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status SMALLINT NOT NULL DEFAULT 1
);

COMMENT ON TABLE auth_third_party IS '第三方登录表';
COMMENT ON COLUMN auth_third_party.id IS 'ID';
COMMENT ON COLUMN auth_third_party.user_id IS '用户ID';
COMMENT ON COLUMN auth_third_party.platform IS '平台类型：wechat、dingtalk、wecom';
COMMENT ON COLUMN auth_third_party.open_id IS '第三方平台OpenID';
COMMENT ON COLUMN auth_third_party.union_id IS '第三方平台UnionID';
COMMENT ON COLUMN auth_third_party.nickname IS '昵称';
COMMENT ON COLUMN auth_third_party.avatar IS '头像';
COMMENT ON COLUMN auth_third_party.gender IS '性别：0-未知 1-男 2-女';
COMMENT ON COLUMN auth_third_party.bind_time IS '绑定时间';
COMMENT ON COLUMN auth_third_party.status IS '状态：0-禁用 1-启用';

CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_third_party_platform_openid ON auth_third_party(platform, open_id);
CREATE INDEX IF NOT EXISTS idx_auth_third_party_user_id ON auth_third_party(user_id);
CREATE INDEX IF NOT EXISTS idx_auth_third_party_platform ON auth_third_party(platform);
