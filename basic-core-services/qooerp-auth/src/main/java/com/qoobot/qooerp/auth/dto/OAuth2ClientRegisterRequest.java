package com.qoobot.qooerp.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * OAuth2客户端注册请求
 */
@Data
public class OAuth2ClientRegisterRequest {

    /**
     * 客户端名称
     */
    @NotBlank(message = "客户端名称不能为空")
    private String clientName;

    /**
     * 客户端类型: confidential-机密客户端(需要secret), public-公开客户端(如SPA/移动App,不需要secret)
     */
    @NotBlank(message = "客户端类型不能为空")
    private String clientType;

    /**
     * 授权类型: authorization_code, refresh_token, client_credentials, password
     */
    @NotEmpty(message = "授权类型不能为空")
    private List<String> authorizationGrantTypes;

    /**
     * 授权范围: openid, profile, email等
     */
    @NotEmpty(message = "授权范围不能为空")
    private List<String> scopes;

    /**
     * 重定向URI列表
     */
    @NotEmpty(message = "重定向URI不能为空")
    private List<String> redirectUris;

    /**
     * 登出后重定向URI列表
     */
    private List<String> postLogoutRedirectUris;

    /**
     * 是否需要用户授权确认
     */
    private Boolean requireAuthorizationConsent = true;

    /**
     * 是否需要PKCE(Proof Key for Code Exchange,适用于公开客户端)
     */
    private Boolean requireProofKey = false;

    /**
     * 访问令牌过期时间(秒),默认7200秒(2小时)
     */
    private Integer accessTokenTimeToLive = 7200;

    /**
     * 刷新令牌过期时间(秒),默认604800秒(7天)
     */
    private Integer refreshTokenTimeToLive = 604800;

    /**
     * 授权码过期时间(秒),默认300秒(5分钟)
     */
    private Integer authorizationCodeTimeToLive = 300;

    /**
     * 访问令牌格式: self-contained(自包含JWT), reference(引用式令牌)
     */
    private String accessTokenFormat = "self-contained";
}
