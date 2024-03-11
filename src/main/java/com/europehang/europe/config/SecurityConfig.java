package com.europehang.europe.config;

import com.europehang.europe.jwt.JwtAccessDeniedHandler;
import com.europehang.europe.jwt.JwtAuthenticationEntryPoint;
import com.europehang.europe.jwt.JwtSecurityConfig;
import com.europehang.europe.jwt.JwtTokenProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            JwtTokenProvider jwtTokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        String[] SWAGGER_URI = {
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.index.html",
                "/webjars/**",
                "/swagger-resources/**"
        };

        return http
                .csrf(AbstractHttpConfigurer::disable)
                // 세션 관리 상태 없음
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler))
                .headers((header) ->
                        header.frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                )
                // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다는 의미.
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(SWAGGER_URI).permitAll()
                        .requestMatchers("/resources/**").permitAll()
                        .requestMatchers("/signUp").permitAll()
                        .requestMatchers("/user/check-email").permitAll()
                        .requestMatchers("/signIn").permitAll()
                        .requestMatchers("/posts/**").permitAll()
                        .requestMatchers("/token/reissue").permitAll()
                        .anyRequest().authenticated() // 나머지 요청들은 모두 인증 되어야한다.
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(new SimpleUrlAuthenticationSuccessHandler("/login")) // 수정 필요
                        .permitAll()
                )
                .with(new JwtSecurityConfig(jwtTokenProvider), customizer -> {})
                .build();
    }
}
