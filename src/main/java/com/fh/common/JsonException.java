package com.fh.common;

import com.fh.common.Interceptor.CountException;
import com.fh.common.Interceptor.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class JsonException {
    /**
     * 处理所有不可知异常
     *
     * @param e 异常
     * @return json结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public jsonData handleException(Exception e) {
        e.printStackTrace();
        return jsonData.getJsonError(e.getMessage());
    }

    @ExceptionHandler(LoginException.class) //
    @ResponseBody
    public jsonData handleNoLoginException(LoginException e){
        e.printStackTrace();
        return jsonData.getJsonError(1000,e.getMessage());
    }


    @ExceptionHandler(CountException.class) //
    @ResponseBody
    public jsonData handleCountException(CountException e){
        return jsonData.getJsonError(2000,e.getMessage());
    }


}
