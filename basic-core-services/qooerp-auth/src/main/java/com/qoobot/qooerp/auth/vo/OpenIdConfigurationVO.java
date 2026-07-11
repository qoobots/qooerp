package com.qoobot.qooerp.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * OIDC 发现端点配置视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenIdConfigurationVO {

    /**
     * 发布者标识符
     */
    private String issuer;

    /**
     * 授权端点
     */
    private String authorizationEndpoint;

    /**
     * 令牌端点
     */
    private String tokenEndpoint;

    /**
     * JWKS 端点
     */
    private String jwksUri;

    /**
     * 用户信息端点
     */
    private String userInfoEndpoint;

    /**
     * 支持的响应类型
     */
    private List<String> responseTypesSupported;

    /**
     * 支持的主题类型
     */
    private List<String> subjectTypesSupported;

    /**
     * 支持的 ID Token 签名算法
     */
    private List<String> idTokenSigningAlgValuesSupported;

    /**
     * 支持的授权范围
     */
    private List<String> scopesSupported;

    /**
     * 支持的授权类型
     */
    private List<String> grantTypesSupported;

    /**
     * 支持的令牌端点认证方法
     */
    private List<String> tokenEndpointAuthMethodsSupported;

    /**
     * 声明支持
     */
    private List<String> claimsSupported;

    /**
     * 注册端点
     */
    private String registrationEndpoint;
}
