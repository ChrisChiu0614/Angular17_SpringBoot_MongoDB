package com.chris.spring.data.mongodb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置類
 *
 * 這個類負責設定 Spring Security 的安全機制，包含：
 * - 禁用 CSRF
 * - 設定 JWT 驗證
 * - 允許未驗證的 API 路徑
 * - 強制所有其他請求需經過身份驗證
 */
@Configuration
//@EnableWebSecurity
@EnableMethodSecurity
//        (securedEnabled = true,
//        jsr250Enabled = true,
//        prePostEnabled = true) //by default
public class WebSecurityConfig {
    // 使用者詳細資料服務 (用於自訂身份驗證)
//    @Autowired
//    UserDetailsServiceImpl userDetailsService;

    // 自訂的未授權處理程序，當 JWT 驗證失敗時會進入這裡
//    @Autowired
//    private AuthEntryPointJwt unauthorizedHandler;

    /**
     * JWT 認證過濾器
     * - 這個過濾器會攔截請求，檢查是否帶有有效的 JWT Token
     * - 如果 Token 驗證通過，則設置 Spring Security 的認證上下文
     */
//    @Bean
//    public AuthTokenFilter authenticationJwtTokenFilter() {
//        return new AuthTokenFilter();
//    }

    /**
     * 身份驗證管理器 (AuthenticationManager)
     * - 用於處理身份驗證的核心組件
     * - 這裡使用 Spring Security 提供的 `AuthenticationConfiguration` 來取得預設的 `AuthenticationManager`
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * 密碼編碼器
     * - 使用 BCrypt 來進行密碼加密與驗證
     * - 建議所有應用程式都應該使用密碼加密來存儲使用者密碼
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * SecurityFilterChain (核心安全配置)
     * - 定義了應用程式的安全性過濾規則
     * - 這裡設定了：
     *   1. 禁用 CSRF (因為我們使用 JWT，不需要 CSRF 保護)
     *   2. 設定未授權處理 (`AuthEntryPointJwt`)
     *   3. 設定無狀態 (Stateless) 會話管理
     *   4. 定義授權規則：
     *      - `/api/auth/**` 允許所有請求 (註冊、登入)
     *      - `/api/test/**` 允許所有請求 (測試 API)
     *      - 其他請求需要經過身份驗證
     *   5. 設定 `authenticationProvider()`
     *   6. 在 `UsernamePasswordAuthenticationFilter` 之前添加 `JWT` 過濾器
     */
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable()) // 禁用 CSRF (因為 JWT 不需要)
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // 設定未授權時的處理方式
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 使用無狀態會話 (Stateless)
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers("/api/auth/**").permitAll() // 允許所有人訪問 `/api/auth/**`
//                                .requestMatchers("/api/test/**").permitAll() // 允許所有人訪問 `/api/test/**`
//                                .anyRequest().authenticated() // 其他請求需要身份驗證
//                );
//
//        // 設定自訂的身份驗證提供者
//        http.authenticationProvider(authenticationProvider());
//
//        // 在 UsernamePasswordAuthenticationFilter 之前添加 JWT 過濾器
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
}
