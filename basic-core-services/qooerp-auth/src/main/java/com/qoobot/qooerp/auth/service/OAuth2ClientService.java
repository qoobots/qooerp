package com.qoobot.qooerp.auth.service;

import com.qoobot.qooerp.auth.dto.OAuth2ClientRegisterRequest;
import com.qoobot.qooerp.auth.dto.OAuth2ClientResponse;
import com.qoobot.qooerp.auth.dto.OAuth2ClientVO;

import java.util.List;

/**
 * OAuth2客户端管理服务接口
 */
public interface OAuth2ClientService {

    /**
     * 注册OAuth2客户端
     *
     * @param request 注册请求
     * @return 客户端响应信息(包含client_secret,仅注册时返回一次)
     */
    OAuth2ClientResponse registerClient(OAuth2ClientRegisterRequest request);

    /**
     * 删除OAuth2客户端
     *
     * @param clientId 客户端标识
     */
    void deleteClient(String clientId);

    /**
     * 获取客户端列表
     *
     * @return 客户端列表
     */
    List<OAuth2ClientVO> listClients();

    /**
     * 根据client_id获取客户端详情
     *
     * @param clientId 客户端标识
     * @return 客户端详情
     */
    OAuth2ClientVO getClientById(String clientId);

    /**
     * 重置客户端密钥
     *
     * @param clientId 客户端标识
     * @return 新的client_secret
     */
    String resetClientSecret(String clientId);
}
