package com.qoobot.qooerp.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 设备视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceVO {

    /**
     * 设备ID
     */
    private Long id;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型：PC/MOBILE/TABLET/APP
     */
    private String deviceType;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 是否记住设备
     */
    private Boolean remembered;

    /**
     * 是否活跃
     */
    private Boolean active;

    /**
     * 最后活跃时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastActiveTime;

    /**
     * 设备类型中文描述
     */
    public String getDeviceTypeName() {
        if (deviceType == null) {
            return "未知设备";
        }
        return switch (deviceType) {
            case "PC" -> "电脑浏览器";
            case "MOBILE" -> "手机浏览器";
            case "TABLET" -> "平板电脑";
            case "APP" -> "移动应用";
            default -> "未知设备";
        };
    }
}
