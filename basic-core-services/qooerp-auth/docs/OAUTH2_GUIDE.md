# OAuth 2.0 + OIDC + JWT 使用指南

## 架构说明

本系统实现了完整的 OAuth 2.0 授权服务器，支持以下特性：

- OAuth 2.0 四种授权模式
- OpenID Connect (OIDC) 1.0
- JWT (JSON Web Token) 作为令牌载体
- JWKS (JSON Web Key Set) 公钥发布

## 授权模式

### 1. 授权码模式 (Authorization Code)

最安全、最推荐的模式，适用于有后端的应用。

**步骤：**
1. 重定向用户到授权端点
2. 用户登录并授权
3. 重定向回应用并附带授权码
4. 使用授权码交换令牌

**请求示例：**
```
GET /oauth2/authorize?
  response_type=code&
  client_id=qooerp-client&
  redirect_uri=http://127.0.0.1:8080/callback&
  scope=openid profile email&
  state=xyz123
```

**重要说明:**
- `redirect_uri` 参数只能传递一个 URI,且该 URI 必须在客户端配置的 `redirect_uris` 列表中
- 如果客户端配置了多个重定向 URI(如 `http://localhost:3000/callback http://localhost:3001/callback`),客户端在发起授权请求时应该选择其中一个作为 `redirect_uri` 参数
- 不能传递多个 URI 或传递整个 URI 列表

**响应示例：**
```
HTTP/1.1 302 Found
Location: http://127.0.0.1:8080/callback?code=SplxlOBeZQQYbYS6WxSbIO&state=xyz123
```

**交换令牌：**
```
POST /oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&
code=SplxlOBeZQQYbYS6WxSbIO&
redirect_uri=http://127.0.0.1:8080/callback&
client_id=qooerp-client&
client_secret=qooerp-secret
```

### 2. 密码模式 (Password)

适用于受信任的客户端（如官方移动应用）。

**请求示例：**
```
POST /oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=password&
username=admin&
password=123456&
client_id=qooerp-client&
client_secret=qooerp-secret&
scope=openid profile email
```

**响应示例：**
```json
{
  "code": 200,
  "data": {
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refresh_token": "e5b2e1f5-1234-4567-89ab-cdef01234567",
    "token_type": "Bearer",
    "expires_in": 7200,
    "scope": "openid profile email",
    "id_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

### 3. 客户端凭证模式 (Client Credentials)

适用于服务间调用（机器对机器）。

**请求示例：**
```
POST /oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=client_credentials&
client_id=qooerp-client&
client_secret=qooerp-secret&
scope=read write
```

### 4. 刷新令牌 (Refresh Token)

使用 refresh_token 获取新的 access_token。

**请求示例：**
```
POST /oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=refresh_token&
refresh_token=e5b2e1f5-1234-4567-89ab-cdef01234567&
client_id=qooerp-client&
client_secret=qooerp-secret
```

## OIDC 端点

### 发现端点
获取 OpenID Connect 配置信息。

```
GET /.well-known/openid-configuration
```

**响应示例：**
```json
{
  "issuer": "http://localhost:8001/auth",
  "authorization_endpoint": "http://localhost:8001/auth/oauth2/authorize",
  "token_endpoint": "http://localhost:8001/auth/oauth2/token",
  "jwks_uri": "http://localhost:8001/auth/.well-known/jwks.json",
  "userinfo_endpoint": "http://localhost:8001/auth/oauth2/userinfo",
  "response_types_supported": ["code", "token"],
  "subject_types_supported": ["public"],
  "id_token_signing_alg_values_supported": ["RS256"],
  "scopes_supported": ["openid", "profile", "email", "read", "write"],
  "grant_types_supported": [
    "authorization_code",
    "refresh_token",
    "client_credentials",
    "password"
  ]
}
```

### JWKS 端点
获取用于验证 JWT 签名的公钥。

```
GET /.well-known/jwks.json
```

### UserInfo 端点
使用 access_token 获取用户信息。

```
GET /oauth2/userinfo
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
```

**响应示例：**
```json
{
  "sub": "user123",
  "name": "张三",
  "email": "zhangsan@example.com",
  "preferred_username": "zhangsan"
}
```

## 客户端配置

### 注册的客户端
- **Client ID**: `qooerp-client`
- **Client Secret**: `qooerp-secret`
- **授权类型**: 
  - Authorization Code
  - Refresh Token
  - Client Credentials
  - Password
- **Redirect URIs**: 
  - `http://127.0.0.1:8080/login/oauth2/code/qooerp-client`
  - `http://127.0.0.1:8080/authorized`
- **Scopes**: `openid`, `profile`, `email`, `read`, `write`

### 令牌有效期
- **Access Token**: 2 小时 (7200 秒)
- **Refresh Token**: 7 天
- **ID Token**: 与 Access Token 相同

## 安全最佳实践

1. **使用授权码模式**：对于有后端的 Web 应用
2. **使用 PKCE**：对于移动端和 SPA 应用
3. **验证 Token 签名**：使用 JWKS 端点获取公钥验证 JWT
4. **刷新令牌**：不要在 refresh_token 过期后使用 access_token
5. **HTTPS**：生产环境必须使用 HTTPS
6. **范围限制**：只请求必要的 scope
7. **撤销令牌**：登出时调用 `/oauth2/revoke` 撤销令牌

## 错误响应

### 授权码错误
```
HTTP/1.1 302 Found
Location: http://callback?error=invalid_request&error_description=missing+client_id&state=xyz123
```

### 令牌错误
```json
{
  "error": "invalid_grant",
  "error_description": "授权码无效或已过期"
}
```

## 常见错误码

| 错误码 | 说明 |
|--------|------|
| `invalid_request` | 请求缺少必需参数 |
| `invalid_client` | 客户端认证失败 |
| `invalid_grant` | 授权类型无效或授权码无效 |
| `unauthorized_client` | 客户端未被授权使用此授权类型 |
| `unsupported_grant_type` | 不支持的授权类型 |
| `invalid_scope` | 请求的 scope 无效 |

## 测试工具

### 使用 curl 测试

```bash
# 密码模式
curl -X POST http://localhost:8001/auth/oauth2/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "username=admin" \
  -d "password=123456" \
  -d "client_id=qooerp-client" \
  -d "client_secret=qooerp-secret" \
  -d "scope=openid profile email"

# 获取用户信息
curl -X GET http://localhost:8001/auth/oauth2/userinfo \
  -H "Authorization: Bearer <access_token>"
```

### 使用 Postman

1. 创建新的请求
2. 选择 POST 方法
3. URL: `http://localhost:8001/auth/oauth2/token`
4. Header: `Content-Type: application/x-www-form-urlencoded`
5. Body (x-www-form-urlencoded):
   - grant_type: `password`
   - username: `admin`
   - password: `123456`
   - client_id: `qooerp-client`
   - client_secret: `qooerp-secret`
   - scope: `openid profile email`
6. 发送请求

## Swagger 文档

访问 `http://localhost:8001/auth/swagger-ui.html` 查看 API 文档。
