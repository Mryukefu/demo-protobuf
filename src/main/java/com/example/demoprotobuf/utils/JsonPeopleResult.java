package com.example.demoprotobuf.utils;

import com.example.demoprotobuf.entry.Person;
import com.example.demoprotobuf.entry.contanst.ResultEnum;
import lombok.Data;


@Data
public class JsonPeopleResult extends JsonResult {
    private Person person;

    public JsonPeopleResult() {
        super();
    }
    public static JsonResult success(Person person) {
        JsonPeopleResult jsonResult = new JsonPeopleResult();
        jsonResult.setCode(ResultEnum.SUCCESS.getCode());
        jsonResult.setMessage(ResultEnum.SUCCESS.getMsg());
        jsonResult.setPerson(person);
        return jsonResult;
    }

}
