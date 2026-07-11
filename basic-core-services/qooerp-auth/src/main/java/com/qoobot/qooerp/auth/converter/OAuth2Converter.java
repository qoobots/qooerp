package com.qoobot.qooerp.auth.converter;

import com.qoobot.qooerp.auth.dto.TokenResponse;
import com.qoobot.qooerp.auth.entity.AuthUser;
import com.qoobot.qooerp.auth.vo.OpenIdConfigurationVO;
import com.qoobot.qooerp.auth.vo.TokenVO;
import com.qoobot.qooerp.auth.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OAuth2 对象转换器
 */
public class OAuth2Converter {

    /**
     * TokenResponse → TokenVO
     */
    public static TokenVO toTokenVO(TokenResponse response) {
        if (response == null) {
            return null;
        }
        return TokenVO.builder()
                .accessToken(response.getAccessToken())
                .refreshToken(response.getRefreshToken())
                .tokenType(response.getTokenType())
                .expiresIn(response.getExpiresIn())
                .scope(response.getScope())
                .idToken(response.getIdToken())
                .build();
    }

    /**
     * AuthUser + Jwt → UserInfoVO
     */
    public static UserInfoVO toUserInfoVO(AuthUser user, Jwt jwt) {
        if (user == null) {
            return null;
        }

        List<String> roles = null;
        Object authoritiesObj = jwt.getClaim("authorities");
        if (authoritiesObj != null) {
            // authorities 可能是 List<Map> 或 List<String>
            if (authoritiesObj instanceof List) {
                List<?> authoritiesList = (List<?>) authoritiesObj;
                if (!authoritiesList.isEmpty()) {
                    roles = authoritiesList.stream()
                            .map(auth -> {
                                if (auth instanceof Map) {
                                    Map<?, ?> authMap = (Map<?, ?>) auth;
                                    Object roleObj = authMap.get("role");
                                    return roleObj != null ? roleObj.toString().replace("ROLE_", "") : "";
                                } else {
                                    return auth.toString().replace("ROLE_", "");
                                }
                            })
                            .filter(role -> !role.isEmpty())
                            .collect(Collectors.toList());
                }
            }
        }

        return UserInfoVO.builder()
                .sub(String.valueOf(user.getId()))
                .name(user.getRealName())
                .givenName(user.getRealName())
                .familyName("")
                .email(user.getEmail())
                .emailVerified(user.getEmail() != null && !user.getEmail().isEmpty())
                .preferredUsername(user.getUsername())
                .phoneNumber(user.getPhone())
                .phoneNumberVerified(user.getPhone() != null && !user.getPhone().isEmpty())
                .tenantId(user.getTenantId())
                .picture(user.getAvatar())
                .roles(roles)
                .build();
    }

    /**
     * TokenVO → Map (用于客户端凭证模式移除敏感字段)
     */
    public static Map<String, Object> toMap(TokenVO tokenVO, boolean includeRefreshToken, boolean includeIdToken) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", tokenVO.getAccessToken());
        map.put("token_type", tokenVO.getTokenType());
        map.put("expires_in", tokenVO.getExpiresIn());
        map.put("scope", tokenVO.getScope());

        if (includeRefreshToken) {
            map.put("refresh_token", tokenVO.getRefreshToken());
        }

        if (includeIdToken) {
            map.put("id_token", tokenVO.getIdToken());
        }

        return map;
    }

    /**
     * 构建默认的 OpenIdConfigurationVO
     */
    public static OpenIdConfigurationVO buildDefaultOpenIdConfiguration(String baseUrl) {
        return OpenIdConfigurationVO.builder()
                .issuer(baseUrl + "/auth")
                .authorizationEndpoint(baseUrl + "/auth/oauth2/authorize")
                .tokenEndpoint(baseUrl + "/auth/oauth2/token")
                .jwksUri(baseUrl + "/auth/.well-known/jwks.json")
                .userInfoEndpoint(baseUrl + "/auth/oauth2/userinfo")
                .responseTypesSupported(Arrays.asList("code", "token"))
                .subjectTypesSupported(Arrays.asList("public"))
                .idTokenSigningAlgValuesSupported(Arrays.asList("RS256"))
                .scopesSupported(Arrays.asList("openid", "profile", "email", "read", "write"))
                .grantTypesSupported(Arrays.asList("authorization_code", "refresh_token", "client_credentials", "password"))
                .tokenEndpointAuthMethodsSupported(Arrays.asList("client_secret_basic", "client_secret_post"))
                .claimsSupported(Arrays.asList("sub", "name", "email", "preferred_username", "phone_number"))
                .build();
    }
}
