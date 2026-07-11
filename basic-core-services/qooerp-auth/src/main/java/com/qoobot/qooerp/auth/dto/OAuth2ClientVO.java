package com.qoobot.qooerp.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * OAuth2客户端详情
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2ClientVO {

    /**
     * 客户端ID(系统生成的UUID)
     */
    private String id;

    /**
     * 客户端标识(用户可识别的ID)
     */
    private String clientId;

    /**
     * 客户端ID颁发时间
     */
    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户端密钥(脱敏显示)
     */
    private String clientSecret;

    /**
     * 客户端密钥过期时间
     */
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端认证方式
     */
    private String clientAuthenticationMethods;

    /**
     * 授权类型
     */
    private String authorizationGrantTypes;

    /**
     * 重定向URI列表
     */
    private String redirectUris;

    /**
     * 登出后重定向URI列表
     */
    private String postLogoutRedirectUris;

    /**
     * 授权范围
     */
    private String scopes;

    /**
     * 客户端设置
     */
    private String clientSettings;

    /**
     * 令牌设置
     */
    private String tokenSettings;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
