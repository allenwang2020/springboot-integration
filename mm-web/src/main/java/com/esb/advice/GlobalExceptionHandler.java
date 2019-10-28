package com.esb.advice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mm.exception.GlobalException;
import org.mm.result.CodeMsg;
import org.mm.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定義全域Exception攔截器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)//攔截所有Excpeiton
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return Result.error(ex.getCodeMsg());
        }else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();//綁定錯誤返回很多錯誤，是一個錯誤列表，只需要第一個錯誤
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));//給狀態碼填入參數
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }

    }
}