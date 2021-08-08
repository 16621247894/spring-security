package com.zwq.cloud.exception;

import com.zwq.cloud.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ExceptionHandle {
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    // 实体校验异常捕获
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response handler(MethodArgumentNotValidException e) {

        BindingResult result = e.getBindingResult();
        ObjectError objectError = result.getAllErrors().stream().findFirst().get();

        log.error("实体校验异常：----------------{}", objectError.getDefaultMessage());
        return Response.fail(objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Response handler(IllegalArgumentException e) {
        log.error("Assert异常：----------------{}", e.getMessage());
        return Response.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Response handler(RuntimeException e) {
        log.error("运行时异常：----------------{}", e.getMessage());
        e.printStackTrace();
        return Response.fail(e.getMessage());
    }

    /*@ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public Response handler(AccessDeniedException e) {
        log.info("security权限不足：----------------{}", e.getMessage());
        return Response.fail("权限不足");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response handler(MethodArgumentNotValidException e) {
        log.info("实体校验异常：----------------{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Response.fail(objectError.getDefaultMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Response handler(IllegalArgumentException e) {
        log.error("Assert异常：----------------{}", e.getMessage());
        return Response.fail(e.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Response handler(RuntimeException e) {
        log.error("运行时异常：----------------{}", e);
        return Response.fail(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Response<Void> handle(HttpServletResponse response, Exception exception) {
        if (exception instanceof CommonException) {
            CommonException commonException = (CommonException) exception;
            return Response.fail(commonException.getCode(), commonException.getMessage());
        } else if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) exception;
            String msg = Objects.requireNonNull(validException.getBindingResult().getFieldError()).getDefaultMessage();
            return Response.fail(202, msg);
        } else{
            logger.error("异常：" + exception.getMessage());
            //exception.printStackTrace();
            return Response.fail(-1, "异常:" + exception.getMessage());
        }

    }*/
}
