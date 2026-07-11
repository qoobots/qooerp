package com.qoobot.qooerp.auth.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qoobot.qooerp.auth.entity.AuthUser;
import com.qoobot.qooerp.auth.mapper.AuthUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * OAuth2 用户详情服务
 */
@Service
@RequiredArgsConstructor
public class OAuth2UserDetailsService implements UserDetailsService {

    private final AuthUserMapper authUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getUsername, username)
                .eq(AuthUser::getDeleted, 0)
                .last("LIMIT 1"));

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new UsernameNotFoundException("用户已被禁用: " + username);
        }

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .accountLocked(user.getLockTime() != null)
                .credentialsExpired(false)
                .accountExpired(false)
                .disabled(user.getStatus() == 0)
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_USER")
                ))
                .build();
    }
}
