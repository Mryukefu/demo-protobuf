package com.example.demoprotobuf.controller;
import com.example.demoprotobuf.annotation.ProtobufResponseModule;
import com.example.demoprotobuf.annotation.ProtobufRequestModule;
import com.example.demoprotobuf.protoc.PersonResultProto;
import com.example.demoprotobuf.utils.JsonResult;
import com.example.demoprotobuf.utils.ProtoBeanUtils;
import com.example.demoprotobuf.utils.ResultEnum;
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


    @PostMapping(value="/demo/test",produces = "application/x-protobuf")
    @ProtobufResponseModule(proToBean = PersonResultProto.PersonResult.class,priLog = 1)
    public JsonResult getPersonProto(@ProtobufRequestModule(proToBeanClass = PersonResultProto.PersonResult.class,
            paramPojoBeanClass = JsonResult.class) JsonResult jsonResult){
            //return JsonResult.fail("失败了");
            return  jsonResult;

    }


    @PostMapping(value = "/demo/param")
    @ResponseBody
    public   JsonResult   param() throws IOException, URISyntaxException {
        PersonResultProto.PersonResult personResult = getBuilder();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URI uri = new URI("http", null, "127.0.0.1", 9988, "/protobuf/demo/test", "", null);
        HttpPost post = new HttpPost(uri);
        post.setEntity(new ByteArrayEntity(personResult.toByteArray()));
        post.setHeader("Content-Type", "application/x-protobuf");
        HttpResponse response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();
        System.out.println(entity);
        JsonResult jsonResult = null;
        if (response.getStatusLine().getStatusCode() == 200) {
            PersonResultProto.PersonResult resp = PersonResultProto.PersonResult.parseFrom(response.getEntity().getContent());
            jsonResult = ProtoBeanUtils.toPojoBean(JsonResult.class, resp);
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
