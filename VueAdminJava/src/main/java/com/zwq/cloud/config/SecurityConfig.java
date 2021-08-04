package com.zwq.cloud.config;

import com.zwq.cloud.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * EnableGlobalMethodSecurity 请求之前都会去做权限的校验
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 登录失败
     */
    @Autowired
    private LoginFailureHandler loginFailureHandler;

    /**
     * 登录成功
     */
    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    /**
     * 没有登录的时候判断
     */
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * 判断权限
     */
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 密码加密
     */
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserDetailServiceImpl userDetailService;


    /**
     * 验证码拦截器
     */
    @Autowired
    CaptchaFilter captchaFilter;

    /**
     * 白名单配置
     */
    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",

    };

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.cors().and().csrf().disable()

                // 登录配置
                .formLogin()
                // 成功处理器
                .successHandler(loginSuccessHandler)
                // 失败处理器
                .failureHandler(loginFailureHandler)

                // 禁用session

                .and().sessionManagement()
                // 不生成session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)


                // 配置拦截规则
                .and().authorizeRequests()
                // 白名单可以访问
                .antMatchers(URL_WHITELIST).permitAll()

                // 必须登录才能访问
                .anyRequest().authenticated()

                // 异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 配置自定义的过滤器

                .and()
                .addFilter(jwtAuthenticationFilter())
                // 在用户名密码之前过滤验证码
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);

    }


    /**
     * 自定义用户密码验证法
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);

    }
}
