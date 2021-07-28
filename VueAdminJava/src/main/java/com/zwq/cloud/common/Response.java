package com.zwq.cloud.common;

import com.zwq.cloud.ResponseStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Response<T> {
    @ApiModelProperty("状态码")
    private int code;
    @ApiModelProperty("消息")
    private String message;
    @ApiModelProperty("返回数据")
    private T data;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 业务成功返回业务代码和描述信息
     */
    public static Response<Void> success() {
        return new Response<Void>(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), null);
    }

    /**
     * 业务成功返回业务代码,描述和返回的参数
     */
    public static <T> Response<T> success(T data) {
        return new Response<T>(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), data);
    }

    /**
     * 业务成功返回业务代码,描述和返回的参数
     */
    public static <T> Response<T> success(ResponseStatus ResponseStatus, T data) {
        if (ResponseStatus == null) {
            return success(data);
        }
        return new Response<T>(ResponseStatus.getCode(), ResponseStatus.getMsg(), data);
    }

    /**
     * 业务异常返回业务代码和描述信息
     */
    public static <T> Response<T> fail() {
        return new Response<T>(ResponseStatus.INTERNAL_SERVER_ERROR.getCode(), ResponseStatus.INTERNAL_SERVER_ERROR.getMsg(), null);
    }


    /**
     * 业务异常返回业务代码,描述和返回的参数
     */
    public static <T> Response<T> fail(T data) {
        return new Response<T>(ResponseStatus.INTERNAL_SERVER_ERROR.getCode(), ResponseStatus.INTERNAL_SERVER_ERROR.getMsg(), data);
    }

    /**
     * 业务其他情况返回业务代码和描述信息
     */
    public static <T> Response<T> result(ResponseStatus responseStatus, T data) {
        return new Response<T>(responseStatus.getCode(), responseStatus.getMsg(), data);
    }

    /**
     * 业务其他情况返回业务代码和描述信息
     */
    public static Response<Void> result(ResponseStatus responseStatus) {
        return new Response<Void>(responseStatus.getCode(), responseStatus.getMsg(), null);
    }

    public static <T> Response<T> fail(int code, String message) {
        return new Response<T>(code, message);
    }
    public static <T> Response<T> fail(String message) {
        return new Response<T>(-1, message);
    }
}
