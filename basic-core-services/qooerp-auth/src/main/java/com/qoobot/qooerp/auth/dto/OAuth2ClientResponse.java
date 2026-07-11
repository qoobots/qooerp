package com.qoobot.qooerp.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * OAuth2客户端注册响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2ClientResponse {

    /**
     * 客户端ID(系统生成的唯一标识)
     */
    private String clientId;

    /**
     * 客户端密钥(仅机密客户端返回,需妥善保管)
     */
    private String clientSecret;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端ID颁发时间
     */
    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户端类型: confidential-机密客户端, public-公开客户端
     */
    private String clientType;

    /**
     * 授权类型
     */
    private String authorizationGrantTypes;

    /**
     * 授权范围
     */
    private String scopes;

    /**
     * 重定向URI列表
     */
    private String redirectUris;

    /**
     * 登出后重定向URI列表
     */
    private String postLogoutRedirectUris;

    /**
     * 是否需要PKCE
     */
    private Boolean requireProofKey;

    /**
     * 提示信息
     */
    private String message;
}
