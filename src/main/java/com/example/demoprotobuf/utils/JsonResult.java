package com.example.demoprotobuf.utils;

import lombok.Data;


@Data
public class JsonResult<T> {
    private int code;
    private String message;
    private T data;
    private T data2;
    public JsonResult() {
        this.code = ResultEnum.SUCCESS.getCode();
        this.message = ResultEnum.SUCCESS.getMsg();
    }


    public JsonResult(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMsg();
    }

    public JsonResult(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMsg();
        this.data = data;
    }

    public static JsonResult fail(String message) {
        JsonResult<Object> jsonResult = new JsonResult<>();
        jsonResult.setCode(110);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public JsonResult(ResultEnum resultEnum, T data, T data2) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMsg();
        this.data = data;
        this.data2 = data2;
    }

}
