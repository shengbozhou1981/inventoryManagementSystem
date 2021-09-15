package com.demo.util;

import com.demo.enums.ProductEnum;
import com.demo.exception.ProductException;
import com.demo.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultVo myExceptionHandler(Exception e, HandlerMethod handlerMethod ){
        log.info("application exception: "+e.getMessage().toString());
        return ResultVOUtil.exception("application busy, please try again later");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResultVo dealNullPointerException(NullPointerException e) {
        log.info("application exception: "+e.getMessage().toString());
        return ResultVOUtil.nullPointer(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultVo runtimeException(RuntimeException e) {
        log.info("Runtime exception: "+e.getMessage().toString());
        return ResultVOUtil.exception(e.getMessage());
    }

    @ExceptionHandler(ProductException.class)
    public ResultVo productException(ProductException e) {
        log.info("product related exception: "+e.getMessage().toString());
        return ResultVOUtil.productException(e.getMessage());
    }

}
