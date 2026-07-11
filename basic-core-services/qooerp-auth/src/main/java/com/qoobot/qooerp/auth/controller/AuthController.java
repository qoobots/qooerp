package com.qoobot.qooerp.auth.controller;

import com.qoobot.qooerp.auth.common.response.Result;
import com.qoobot.qooerp.auth.dto.LoginRequest;
import com.qoobot.qooerp.auth.dto.LoginResponse;
import com.qoobot.qooerp.auth.dto.RefreshTokenRequest;
import com.qoobot.qooerp.auth.dto.TokenResponse;
import com.qoobot.qooerp.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器 - 传统登录接口
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "用户登录、登出、令牌刷新等认证接口")
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "支持用户名密码、手机验证码、邮箱验证码等多种登录方式")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("用户登录请求: username={}, loginType={}, ip={}",
                request.getUsername(), request.getLoginType(), request.getIp());

        try {
            LoginResponse response = authService.login(request);
            log.info("用户登录成功: userId={}, username={}", response.getUserId(), response.getUsername());
            return Result.success(response);
        } catch (Exception e) {
            log.error("用户登录失败: username={}, error={}", request.getUsername(), e.getMessage());
            throw e;
        }
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "将当前令牌加入黑名单")
    public Result<Void> logout(@RequestHeader("Authorization") String authorization) {
        log.info("用户登出请求");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return Result.error("invalid_request: 缺少Authorization header");
        }

        String token = authorization.replace("Bearer ", "").trim();
        authService.logout(token);

        log.info("用户登出成功");
        return Result.success();
    }

    /**
     * 刷新令牌
     */
    @PostMapping("/refresh")
    @Operation(summary = "刷新令牌", description = "使用refresh_token换取新的access_token")
    public Result<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("刷新令牌请求");

        TokenResponse response = authService.refreshToken(request);

        log.info("刷新令牌成功");
        return Result.success(response);
    }

    /**
     * 验证令牌
     */
    @GetMapping("/validate")
    @Operation(summary = "验证令牌", description = "验证access_token是否有效")
    public Result<Boolean> validateToken(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return Result.success(false);
        }

        String token = authorization.replace("Bearer ", "").trim();
        boolean valid = authService.validateToken(token);

        return Result.success(valid);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/current")
    @Operation(summary = "获取当前用户信息", description = "根据令牌获取当前登录用户信息", security = @SecurityRequirement(name = "Bearer Authentication"))
    public Result<LoginResponse> getCurrentUser(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return Result.error("invalid_request: 缺少Authorization header");
        }

        String token = authorization.replace("Bearer ", "").trim();
        LoginResponse response = authService.getCurrentUser(token);

        return Result.success(response);
    }
}
