package com.qoobot.qooerp.auth.controller;

import com.qoobot.qooerp.auth.common.response.Result;
import com.qoobot.qooerp.auth.dto.OAuth2ClientRegisterRequest;
import com.qoobot.qooerp.auth.dto.OAuth2ClientResponse;
import com.qoobot.qooerp.auth.dto.OAuth2ClientVO;
import com.qoobot.qooerp.auth.service.OAuth2ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OAuth2客户端管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/oauth2/clients")
@RequiredArgsConstructor
@Tag(name = "OAuth2客户端管理", description = "OAuth2客户端注册、删除、查询等接口")
@SecurityRequirement(name = "Bearer Authentication")
public class OAuth2ClientController {

    private final OAuth2ClientService clientService;

    /**
     * 注册OAuth2客户端
     */
    @PostMapping("/register")
    @Operation(summary = "注册OAuth2客户端", description = "注册新的OAuth2客户端,返回client_id和client_secret")
    public Result<OAuth2ClientResponse> registerClient(@Valid @RequestBody OAuth2ClientRegisterRequest request) {
        log.info("注册OAuth2客户端请求: clientName={}, clientType={}",
                request.getClientName(), request.getClientType());

        OAuth2ClientResponse response = clientService.registerClient(request);

        log.info("OAuth2客户端注册成功: clientId={}", response.getClientId());
        return Result.success(response);
    }

    /**
     * 删除OAuth2客户端
     */
    @DeleteMapping("/{clientId}")
    @Operation(summary = "删除OAuth2客户端", description = "根据client_id删除OAuth2客户端")
    public Result<Void> deleteClient(@PathVariable String clientId) {
        log.info("删除OAuth2客户端请求: clientId={}", clientId);

        clientService.deleteClient(clientId);

        log.info("OAuth2客户端删除成功: clientId={}", clientId);
        return Result.success();
    }

    /**
     * 获取客户端列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取客户端列表", description = "获取所有已注册的OAuth2客户端列表")
    public Result<List<OAuth2ClientVO>> listClients() {
        log.info("获取OAuth2客户端列表请求");

        List<OAuth2ClientVO> clients = clientService.listClients();

        return Result.success(clients);
    }

    /**
     * 获取客户端详情
     */
    @GetMapping("/{clientId}")
    @Operation(summary = "获取客户端详情", description = "根据client_id获取OAuth2客户端详细信息")
    public Result<OAuth2ClientVO> getClient(@PathVariable String clientId) {
        log.info("获取OAuth2客户端详情请求: clientId={}", clientId);

        OAuth2ClientVO client = clientService.getClientById(clientId);

        return Result.success(client);
    }

    /**
     * 重置客户端密钥
     */
    @PostMapping("/{clientId}/reset-secret")
    @Operation(summary = "重置客户端密钥", description = "重置OAuth2客户端的client_secret,仅机密客户端支持")
    public Result<String> resetClientSecret(@PathVariable String clientId) {
        log.info("重置OAuth2客户端密钥请求: clientId={}", clientId);

        String newSecret = clientService.resetClientSecret(clientId);

        log.info("OAuth2客户端密钥重置成功: clientId={}", clientId);
        return Result.success(newSecret);
    }
}
