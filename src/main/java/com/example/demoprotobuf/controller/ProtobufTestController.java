package com.example.demoprotobuf.controller;
import com.alibaba.fastjson.JSON;
import com.example.demoprotobuf.annotation.ProtobufResponseModule;
import com.example.demoprotobuf.annotation.ProtobufRequestModule;
import com.example.demoprotobuf.entry.Person;
import com.example.demoprotobuf.entry.PhoneNumber;
import com.example.demoprotobuf.entry.contanst.LogEnum;
import com.example.demoprotobuf.protoc.PersonResultProto;
import com.example.demoprotobuf.utils.*;
import com.example.demoprotobuf.entry.contanst.ResultEnum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * class desc
 * todo
 *
 * @author ykf
 * @date 2021/5/7 13:18
 */
@Controller
@RequestMapping(value = "protobuf")
public class ProtobufTestController {


    @PostMapping(value="/demo/test")
    @ResponseBody
    @ProtobufResponseModule(proToBean = PersonResultProto.PersonResult.class,priLog = LogEnum.INFO )
    public JsonPeopleResult getPersonProto( @ProtobufRequestModule(proToBeanClass = PersonResultProto.PersonResult.class,priLog = LogEnum.INFO )JsonPeopleResult jsonPeopleResult ){


        return jsonPeopleResult;



    }


    @PostMapping(value = "/demo/param")
    @ResponseBody
    public JsonPeopleResult param() throws IOException, URISyntaxException {
        PersonResultProto.PersonResult personResult = getBuilder();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URI uri = new URI("http", null, "127.0.0.1", 9988, "/protobuf/demo/test", "", null);
        HttpPost post = new HttpPost(uri);
        post.setEntity(new ByteArrayEntity(personResult.toByteArray()));
        post.setHeader("Content-Type", "application/x-protobuf");
        HttpResponse response = httpClient.execute(post);

        JsonPeopleResult jsonResult = null;
        if (response.getStatusLine().getStatusCode() == 200) {
            PersonResultProto.PersonResult resp = PersonResultProto.PersonResult.parseFrom(response.getEntity().getContent());
            jsonResult = ProtoBeanUtils.toPojoBean(JsonPeopleResult.class, resp);
        }
        return jsonResult;

    }

    private PersonResultProto.PersonResult getBuilder() {

        PersonResultProto.PersonResult.Builder personResultBuilder = PersonResultProto.PersonResult.newBuilder();
        personResultBuilder
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMessage(ResultEnum.SUCCESS.getMsg())
                .setPerson(PersonResultProto.Person.newBuilder()
                        .setId(11)
                        .setEmail("1303624897@qq.com")
                        .setName("test")
                        .addPhones(
                                PersonResultProto.Person.PhoneNumber.newBuilder()
                                        .setNumber("110")
                                        .setType(PersonResultProto.Person.PhoneType.MOBILE)
                        )
                );
        return personResultBuilder.build();
    }


}
