package com.zwq.cloud.exception;

import com.zwq.cloud.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandle {
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);


    @ExceptionHandler(value = Exception.class)
    public Response<Void> handle(HttpServletResponse response, Exception exception) {
        if (exception instanceof CommonException) {
            CommonException commonException = (CommonException) exception;
            return Response.fail(commonException.getCode(), commonException.getMessage());
        } else if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) exception;
            String msg = Objects.requireNonNull(validException.getBindingResult().getFieldError()).getDefaultMessage();
            return Response.fail(202, msg);
        } else {
            logger.error("异常：" + exception.getMessage());
            exception.printStackTrace();
            return Response.fail(-1, "异常:" + exception.getMessage());
        }

    }
}
