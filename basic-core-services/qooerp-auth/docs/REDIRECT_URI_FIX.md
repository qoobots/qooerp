# OAuth2 错误修复说明

## 问题描述

当使用 OAuth2 授权码模式时,可能会遇到以下错误:

### 错误 1: redirect_uri 参数错误
```
java.lang.IllegalArgumentException: redirect_uri "http://localhost:3000/callback http://localhost:3001/callback" is not a valid redirect URI or contains fragment
```

### 错误 2: token_settings JSON 格式错误
```
java.lang.ClassCastException: class java.lang.String cannot be cast to class java.time.Duration
```

### 错误 3: scopes 格式错误
```
java.lang.IllegalArgumentException: scope "read write" contains invalid characters
```

## 错误原因

### 错误 1 原因

客户端配置的 `redirect_uris` 字段支持多个 URI,使用空格分隔:
```sql
redirect_uris = 'http://localhost:3000/callback http://localhost:3001/callback'
```

但在发起授权请求时,**只能传递其中一个有效的 URI**,不能传递整个 URI 列表。

**错误示例:**
```
GET /oauth2/authorize?client_id=test-client&redirect_uri=http://localhost:3000/callback http://localhost:3001/callback
```

这样会报错,因为整个字符串被当作一个 URI 来验证。

**正确示例:**
```
GET /oauth2/authorize?client_id=test-client&redirect_uri=http://localhost:3000/callback
```

或

```
GET /oauth2/authorize?client_id=test-client&redirect_uri=http://localhost:3001/callback
```

### 错误 2 原因

数据库中的 `token_settings` JSON 格式不正确。Spring Security OAuth2 Authorization Server 期望 Duration 类型使用 ISO-8601 字符串格式。

**正确格式:**
```json
"settings.token.access-token-time-to-live":"PT7200S"
```

**错误格式** (不要使用):
```json
"settings.token.access-token-time-to-live":{"@class":"java.time.Duration","seconds":7200}
```

### 错误 3 原因

数据库中的 `scopes` 字段包含了包含空格的 scope 名称,如 `read write`。Spring Security OAuth2 要求每个 scope 名称只能包含字母、数字、下划线和短横线。

**错误示例:**
```sql
scopes = 'read write'  -- "read" 和 "write" 作为两个独立的 scope 是可以的
```

**问题**: 如果某个 scope 本身包含空格(如 `"read write"` 作为一个 scope),就会报错。

**正确的 scopes 格式:**
```sql
scopes = 'openid profile email'  -- 使用空格分隔多个独立的 scope
```

## 修复内容

### 1. 添加了验证逻辑

在 `OAuth2Controller` 的 `authorize` 和 `token` 端点中添加了 `redirect_uri` 的验证逻辑:

```java
// 验证 redirect_uri 是否在客户端配置的 redirect_uris 列表中
String registeredRedirectUris = String.join(" ", registeredClient.getRedirectUris());
if (!registeredRedirectUris.contains(redirectUri)) {
    log.warn("无效的 redirect_uri: {}, 客户端配置的合法 URIs: {}", redirectUri, registeredRedirectUris);
    return buildErrorUrl(redirectUri, "invalid_redirect_uri", state);
}
```

### 2. 修复了 token_settings JSON 格式

创建了 `V1.0.13__fix_token_settings_to_simple_format.sql` 迁移脚本,修正了 `token_settings` 字段的 JSON 格式:

**正确格式** (ISO-8601 字符串):
```json
"settings.token.access-token-time-to-live":"PT7200S"
```

**错误格式** (不要使用):
```json
"settings.token.access-token-time-to-live":{"@class":"java.time.Duration","seconds":7200}
```

### 3. 更新了文档

在 `docs/OAUTH2_GUIDE.md` 中添加了关于 `redirect_uri` 参数的说明:

> **重要说明:**
> - `redirect_uri` 参数只能传递一个 URI,且该 URI 必须在客户端配置的 `redirect_uris` 列表中
> - 如果客户端配置了多个重定向 URI(如 `http://localhost:3000/callback http://localhost:3001/callback`),客户端在发起授权请求时应该选择其中一个作为 `redirect_uri` 参数
> - 不能传递多个 URI 或传递整个 URI 列表

### 4. 添加了数据库迁移脚本

创建了以下迁移脚本:
- `V1.0.11__fix_redirect_uris_and_add_comments.sql` - 添加了详细的表和字段注释
- `V1.0.13__fix_token_settings_to_simple_format.sql` - 修复了 token_settings JSON 格式

## 如何避免此错误

### 客户端开发

1. 在客户端应用中,只传递一个 `redirect_uri` 参数
2. 该 `redirect_uri` 必须是客户端注册时配置的 URI 之一
3. 对于多个环境或前端应用,分别注册不同的客户端,或者配置多个 URI 但每次只使用一个

### 测试示例

**密码模式(无需 redirect_uri):**
```bash
curl -X POST http://localhost:8001/auth/oauth2/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "username=admin" \
  -d "password=123456" \
  -d "client_id=test-client" \
  -d "client_secret=test-secret" \
  -d "scope=openid profile email"
```

**授权码模式(需要 redirect_uri):**
```bash
# 第一步:发起授权请求,使用单个 redirect_uri
GET http://localhost:8001/auth/oauth2/authorize?
  response_type=code&
  client_id=test-client&
  redirect_uri=http://localhost:3000/callback&
  scope=openid profile email&
  state=xyz123

# 第二步:使用授权码交换令牌,使用相同的 redirect_uri
POST http://localhost:8001/auth/oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&
code=SplxlOBeZQQYbYS6WxSbIO&
redirect_uri=http://localhost:3000/callback&
client_id=test-client&
client_secret=test-secret
```

## 数据库迁移

应用修复后,运行数据库迁移:

```bash
mvn flyway:migrate
```

这将更新数据库中的客户端配置和注释。

## token_settings JSON 格式规范

Spring Security OAuth2 Authorization Server 要求 `token_settings` 字段使用特定的 JSON 格式:

### Duration 类型
**正确的格式** - 使用 ISO-8601 字符串格式:
```json
"settings.token.access-token-time-to-live":"PT7200S"
```

**常见的时间格式:**
- `PT300S` = 5 分钟 (300 秒)
- `PT7200S` = 2 小时 (7200 秒)
- `PT604800S` = 7 天 (604800 秒)
- `PT86400S` = 1 天 (86400 秒)

### 完整示例
```json
{
  "@class":"java.util.Collections$UnmodifiableMap",
  "settings.token.reuse-refresh-tokens":false,
  "settings.token.access-token-format":{
    "@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat",
    "value":"self-contained"
  },
  "settings.token.access-token-time-to-live":"PT7200S",
  "settings.token.refresh-token-format":{
    "@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat",
    "value":"self-contained"
  },
  "settings.token.refresh-token-time-to-live":"PT604800S",
  "settings.token.authorization-code-time-to-live":"PT300S"
}
```

## 相关文件

- `src/main/java/com/qoobot/qooerp/auth/controller/OAuth2Controller.java` - OAuth2 控制器
- `src/main/resources/db/migration/V1.0.11__fix_redirect_uris_and_add_comments.sql` - 数据库迁移脚本(注释)
- `src/main/resources/db/migration/V1.0.13__fix_token_settings_to_simple_format.sql` - 数据库迁移脚本(修复 JSON)
- `src/main/resources/db/migration/V1.0.14__fix_scopes_format.sql` - 数据库迁移脚本(修复 scopes)
- `docs/OAUTH2_GUIDE.md` - OAuth2 使用指南

## 数据库字段格式规范总结

### redirect_uris
- **格式**: 空格分隔的多个 URI
- **示例**: `http://localhost:3000/callback http://localhost:3001/callback`
- **注意**: 客户端请求时只能传递其中一个 URI

### scopes
- **格式**: 空格分隔的多个 scope 名称
- **示例**: `openid profile email`
- **限制**: 每个 scope 名称只能包含字母、数字、下划线和短横线,不能包含空格
- **错误示例**: `read write` (包含空格) ❌
- **正确示例**: `read write` 作为两个独立的 scope ✅

### token_settings
- **格式**: JSON 对象
- **Duration 类型**: 使用 ISO-8601 字符串格式
- **示例**: `"settings.token.access-token-time-to-live":"PT7200S"`
