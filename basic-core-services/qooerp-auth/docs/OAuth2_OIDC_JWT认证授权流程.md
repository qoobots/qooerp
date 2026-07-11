# OAuth2.0 + OIDC + JWT 认证授权流程

## 概述

本文档描述基于 OAuth2.0 授权码模式、OpenID Connect (OIDC) 和 JWT 的完整认证授权流程。

## 完整流程步骤

### 第1步: 发起授权请求

用户访问客户端服务前端页面,如果未登录,前端构造授权请求并跳转至 Auth 服务授权页面:

**请求参数:**
- `client_id`: 客户端标识
- `redirect_uri`: 授权完成后的回调地址
- `scope`: 请求的权限范围 (如: `openid profile email`)
- `state`: 随机字符串,用于防 CSRF 攻击
- `response_type=code`: 授权码模式

```
GET /oauth2/authorize?client_id=xxx&redirect_uri=xxx&scope=openid%20profile&state=xxx&response_type=code
```

### 第2步: 用户登录 Auth 服务

如果用户未登录 Auth 服务,会重定向至 Auth 登录页面:
- 用户输入用户名和密码
- Auth 服务验证用户身份
- 登录成功后,重定向回授权页面

### 第3步: 用户授权

用户在 Auth 服务授权页面:
- 查看客户端请求的权限信息
- 点击"同意授权"按钮

### 第4步: 返回授权码

用户同意授权后,Auth 服务重定向至客户端的 `redirect_uri`,携带以下参数:
- `code`: 授权码(一次性,有效期很短,通常10分钟)
- `state`: 第1步生成的 state 值,客户端需校验一致性

```
GET {redirect_uri}?code=xxx&state=xxx
```

**注意**: 此时只返回 `code` 和 `state`,不包含 `client_id` 和 `username`

### 第5步: 后端交换令牌

客户端前端获取授权码后,将 `code` 传递给客户端服务后端。后端向 Auth 服务令牌端点发起请求:

**请求参数:**
- `grant_type=authorization_code`: 授权码模式
- `code`: 第4步获取的授权码
- `client_id`: 客户端标识
- `client_secret`: 客户端密钥(机密客户端必需)
- `redirect_uri`: 必须与第1步一致

```http
POST /oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&code=xxx&client_id=xxx&client_secret=xxx&redirect_uri=xxx
```

**响应数据:**
```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIs...",
  "refresh_token": "eyJhbGciOiJSUzI1NiIs...",
  "id_token": "eyJhbGciOiJSUzI1NiIs...",
  "token_type": "Bearer",
  "expires_in": 3600,
  "scope": "openid profile email"
}
```

### 第6步: 验证身份令牌

客户端后端验证 `id_token` (JWT):
- 验证签名
- 验证签发者 (issuer)
- 验证受众 (audience)
- 验证过期时间 (exp)
- 从 payload 中提取用户信息

```json
// id_token payload 示例
{
  "iss": "http://localhost:8001/auth",
  "sub": "user-123",
  "aud": "client-456",
  "exp": 1712345678,
  "iat": 1712342078,
  "name": "张三",
  "email": "zhangsan@example.com",
  "picture": "http://example.com/avatar.jpg"
}
```

### 第7步: 建立会话

客户端后端:
- 保存 `access_token` 和 `refresh_token` 到安全存储
- 根据 `id_token` 中的用户信息建立用户会话 (Session/JWT)
- 返回登录成功响应给前端

前端后续通过会话与后端交互,后端使用 `access_token` 访问资源服务。

### 第8步: 刷新令牌(可选)

当 `access_token` 过期时,客户端后端使用 `refresh_token` 获取新的 `access_token`:

```http
POST /oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=refresh_token&refresh_token=xxx&client_id=xxx&client_secret=xxx
```

## 核心概念说明

### OAuth2.0 令牌

| 令牌 | 用途 | 传输方式 | 有效期 |
|------|------|----------|--------|
| `access_token` | 访问受保护资源的凭证 | HTTP Header: `Authorization: Bearer {token}` | 较短(如1小时) |
| `refresh_token` | 用于刷新过期的 access_token | 服务端安全存储 | 较长(如30天) |
| `id_token` | 身份验证令牌 (OIDC) | 可返回前端,用于获取用户信息 | 与 access_token 相同 |

### state 参数作用

1. **防止 CSRF 攻击**
   - 第1步生成随机 state 并存储(如 session)
   - 第4步回调时校验返回的 state 是否一致

2. **示例:**
```javascript
// 第1步
const state = generateRandomString();
session.state = state;

// 第4步
if (request.query.state !== session.state) {
  throw new Error('Invalid state');
}
```

### PKCE 增强 (推荐用于移动端/SPA)

对于公开客户端(无法安全存储 client_secret),使用 PKCE (Proof Key for Code Exchange):

**第1步:** 生成 code_verifier 和 code_challenge
```javascript
const codeVerifier = generateRandomString(43-128);
const codeChallenge = sha256(codeVerifier);
```

**第1步请求:** 添加 `code_challenge` 和 `code_challenge_method=S256`
```
GET /oauth2/authorize?...&code_challenge=xxx&code_challenge_method=S256
```

**第5步请求:** 添加 `code_verifier`
```
POST /oauth2/token
...
&code_verifier=xxx
```

## 架构模式

### 模式1: BFF (Backend for Frontend) - 推荐

```
前端 → 客户端后端 (Session) → 资源服务 (access_token)
```

- 前端只与客户端后端交互
- access_token 存储在后端,不暴露给前端
- 安全性高,适合传统 Web 应用

### 模式2: SPA (Single Page Application)

```
前端 → 直接使用 access_token → 资源服务
```

- 前端直接使用 access_token 调用 API
- 需注意安全存储(如使用 HttpOnly Cookie 或内存)
- 适合现代 SPA 应用

## 安全注意事项

1. **HTTPS**: 所有通信必须使用 HTTPS
2. **Token 存储**: access_token 和 refresh_token 必须存储在安全位置
3. **State 验证**: 必须验证 state 参数防止 CSRF
4. **Redirect URI**: 必须严格校验 redirect_uri,防止开放重定向
5. **Token 过期**: 合理设置 token 有效期
6. **Scope 限制**: 遵循最小权限原则
7. **PKCE**: 公开客户端必须使用 PKCE

## 错误处理

| 错误码 | 说明 | 处理方式 |
|--------|------|----------|
| `invalid_request` | 请求参数缺失或无效 | 检查请求参数 |
| `unauthorized_client` | 客户端未授权 | 检查客户端配置 |
| `access_denied` | 用户拒绝授权 | 引导用户重新授权 |
| `unsupported_response_type` | 不支持的响应类型 | 检查 response_type 参数 |
| `invalid_scope` | 无效的 scope | 检查 scope 参数 |
| `server_error` | 授权服务器内部错误 | 重试或联系管理员 |
| `temporarily_unavailable` | 服务暂时不可用 | 稍后重试 |

## 参考资料

- [OAuth 2.0 RFC 6749](https://tools.ietf.org/html/rfc6749)
- [OpenID Connect Core 1.0](https://openid.net/specs/openid-connect-core-1_0.html)
- [RFC 7519 - JWT (JSON Web Token)](https://tools.ietf.org/html/rfc7519)
- [PKCE RFC 7636](https://tools.ietf.org/html/rfc7636)
