package com.fh.common;

public class jsonData {

    private Integer code;

    private String message;

    private Object data;
    //成功
    private jsonData(Object data){
        this.code=200;
        this.data=data;
        this.message="success";
    }
    //失败
    private jsonData(String message){
        this.code=500;
        this.message=message;
    }
    private jsonData(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    public static jsonData getJsonError(Integer code,String message){
        return new jsonData(code,message);
    }
    public static jsonData getJsonSuccess(Object data){
        return  new jsonData(data);
    }

    public static jsonData getJsonError(String message){
        return new jsonData(message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
