package com.qoobot.qooerp.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth2_jwk_keys")
public class OAuth2JwkKey {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String keyId;

    private byte[] publicKey;

    private byte[] privateKey;

    private String algorithm;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isActive;
}
