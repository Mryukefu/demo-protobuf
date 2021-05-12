package com.example.demoprotobuf.utils;

import com.example.demoprotobuf.entry.contanst.ResultEnum;
import lombok.Data;


@Data
public class JsonResult<T> {
    protected int code;
    protected String message;
    protected T data;
    protected T data2;
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
