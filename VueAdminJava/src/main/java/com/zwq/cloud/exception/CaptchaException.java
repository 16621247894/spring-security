package com.zwq.cloud.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author wuqing.zhu
 * @date 2021/08/12 13:53
 */
public class CaptchaException extends AuthenticationException {
    public CaptchaException(String msg) {
        super(msg);
    }
}
