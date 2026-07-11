package com.qoobot.qooerp.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 用户设备表
 * @TableName auth_device
 */
@TableName(value ="auth_device")
@Data
public class AuthDevice {
    /**
     * 设备ID
     */
    @TableId
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 设备唯一标识
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 状态：0-禁用 1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    private Integer deleted;
    private String deviceFingerprint;
    private Boolean remembered;
    private Boolean active;
    private Date lastActiveTime;

}