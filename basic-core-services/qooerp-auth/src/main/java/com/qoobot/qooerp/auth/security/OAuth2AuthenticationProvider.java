package com.qoobot.qooerp.auth.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * OAuth2 密码模式认证提供者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationProvider implements AuthenticationProvider {

    private final OAuth2UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        log.debug("OAuth2 密码模式认证: {}", username);

        try {
            // 加载用户详情
            var userDetails = userDetailsService.loadUserByUsername(username);

            // 验证密码
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                log.warn("OAuth2 密码认证失败: {}", username);
                throw new BadCredentialsException("密码错误");
            }

            // 返回认证成功的 Authentication
            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    password,
                    userDetails.getAuthorities()
            );
        } catch (Exception e) {
            log.error("OAuth2 认证异常", e);
            throw new BadCredentialsException("认证失败");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
