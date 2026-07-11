package com.qoobot.qooerp.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OAuth2 令牌视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 令牌类型
     */
    private String tokenType;

    /**
     * 过期时间（秒）
     */
    private Long expiresIn;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * ID Token (OIDC)
     */
    private String idToken;
}
