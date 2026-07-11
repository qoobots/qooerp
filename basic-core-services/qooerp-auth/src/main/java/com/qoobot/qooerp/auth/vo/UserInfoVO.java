package com.qoobot.qooerp.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * OIDC 用户信息视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {

    /**
     * 用户唯一标识符 (Subject)
     */
    private String sub;

    /**
     * 用户真实姓名
     */
    private String name;

    /**
     * 名字
     */
    private String givenName;

    /**
     * 姓氏
     */
    private String familyName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱是否已验证
     */
    private Boolean emailVerified;

    /**
     * 首选用户名
     */
    private String preferredUsername;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 手机号是否已验证
     */
    private Boolean phoneNumberVerified;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 头像
     */
    private String picture;

    /**
     * 角色列表
     */
    private List<String> roles;

    /**
     * 额外属性
     */
    private Map<String, Object> additionalProperties;
}
