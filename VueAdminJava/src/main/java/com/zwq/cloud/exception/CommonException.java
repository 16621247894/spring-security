package com.zwq.cloud.exception;

import com.zwq.cloud.common.Response;

public class CommonException extends RuntimeException {
    private int code;

    private String submsg;

    public CommonException(int code, String message, String submsg, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.submsg = submsg;
    }

    public CommonException(int code, String message, Throwable cause) {
        this(code, message, null, cause);
    }

    public CommonException(int code, String message, String submsg) {
        this(code, message, submsg, null);
    }

    public CommonException(int code, String message) {
        this(code, message, null, null);
    }

    public <T> Response<T> response() {
        return Response.fail(code, this.submsg);
    }

    public int getCode() {
        return code;
    }
}
