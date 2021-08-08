package com.zwq.cloud.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.zwq.cloud.exception.CaptchaException;
import com.zwq.cloud.utils.Constants;
import com.zwq.cloud.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    LoginFailureHandler loginFailureHandler;


    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 获取当前的url
        String url = httpServletRequest.getRequestURI();

        if ("/login".equals(url) && "POST".equals(httpServletRequest.getMethod())) {
            try {
                // 校验验证码
                validate(httpServletRequest);
            } catch (CaptchaException e) {
                // 交给认证失败处理器
                loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 校验验证码逻辑
     *
     * @param httpServletRequest request
     */
    private void validate(HttpServletRequest httpServletRequest) {
        String key = httpServletRequest.getParameter("token");
        String code = httpServletRequest.getParameter("code");
        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
            throw new CaptchaException("验证码错误");
        }
        Object redisCode = redisCache.hGet(Constants.Captcha.CAPTCHA_KEY, key);
        if (!code.equals(redisCode)) {
            throw new CaptchaException("验证码错误");
        }
        // 一次性使用
        redisCache.hDelete(Constants.Captcha.CAPTCHA_KEY, key);
    }
}

