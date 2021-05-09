package com.example.demoprotobuf.utils;

import com.example.demoprotobuf.entry.Person;
import lombok.Data;


@Data
public class JsonResult {
    private int code;
    private String message;
    private Person person;
    public JsonResult() {
        this.code = ResultEnum.SUCCESS.getCode();
        this.message = ResultEnum.SUCCESS.getMsg();
    }

    public static JsonResult fail(String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(110);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static  JsonResult success(ResultEnum resultEnum,Person person) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(ResultEnum.SUCCESS.getCode());
        jsonResult.setMessage(ResultEnum.SUCCESS.getMsg());
        jsonResult.setPerson(person);
        return jsonResult;
    }

}
