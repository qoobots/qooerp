package com.qoobot.qooerp.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;

/**
 * OAuth2注册客户端Mapper
 */
@Mapper
public interface OAuth2RegisteredClientMapper {

    /**
     * 查询所有客户端
     */
    @Select("SELECT * FROM oauth2_registered_client ORDER BY client_id_issued_at DESC")
    List<RegisteredClient> findAll();
}
