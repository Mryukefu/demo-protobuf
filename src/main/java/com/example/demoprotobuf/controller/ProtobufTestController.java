package com.example.demoprotobuf.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demoprotobuf.annotation.ProtobufBodyModule;
import com.example.demoprotobuf.annotation.ProtobufModule;
import com.example.demoprotobuf.entry.AddressBookVO;
import com.example.demoprotobuf.protoc.AddressBookProto;
import com.example.demoprotobuf.service.ProtobufTestService;
import com.example.demoprotobuf.utils.JsonResult;
import com.example.demoprotobuf.utils.ProtoBeanUtils;
import com.example.demoprotobuf.utils.ResultEnum;
import com.google.gson.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;


/**
 * class desc
 * todo
 *
 * @author ykf
 * @date 2021/5/7 13:18
 */
@RestController
@RequestMapping(value = "protobuf")
public class ProtobufTestController {

    private AddressBookVO addressBookVO;


    @Autowired
    private ProtobufTestService protobufTestService;
    @ProtobufBodyModule
    @PostMapping(value="/demo/test",produces = "application/x-protobuf")
    public   byte[] getPersonProto(@ProtobufModule(proToBean =AddressBookProto.AddressBook.class,
                                          pojoBean =AddressBookVO.class ) AddressBookVO addressBookVO) throws IOException {
        AddressBookProto.AddressBook.Builder builder2 = AddressBookProto.AddressBook.newBuilder();
        ProtoBeanUtils.toProtoBean(builder2,addressBookVO);
        byte[] bytes1 = builder2.build().toByteArray();
        for (byte aByte : bytes1) {
            System.out.print(aByte);
        }
        return bytes1;

    }


    @PostMapping(value = "/demo/param")
    public   AddressBookVO   param() throws IOException, URISyntaxException {
        AddressBookProto.Person.Builder personBuilder = AddressBookProto.Person.newBuilder();

        AddressBookProto.Person.PhoneNumber.Builder phoneNumber = AddressBookProto.Person.PhoneNumber.newBuilder();

        phoneNumber.setNumber("1222");
        phoneNumber.setType( AddressBookProto.Person.PhoneType.MOBILE);

        personBuilder.setEmail("11").setId(11).setName("mihi").addPhones(phoneNumber);

        AddressBookProto.AddressBook.Builder builder = AddressBookProto.AddressBook.newBuilder();
        builder.addPeople(personBuilder.build());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URI uri = new URI("http", null, "127.0.0.1", 9988, "/protobuf/demo/test", "", null);
        HttpPost post = new HttpPost(uri);
        post.setEntity(new ByteArrayEntity(builder.build().toByteArray()));
        post.setHeader("Content-Type", "application/x-protobuf");
        HttpResponse response = httpClient.execute(post);

        AddressBookVO addressBookVO = null;
        if (response.getStatusLine().getStatusCode() == 200) {

             AddressBookProto.AddressBook resp = AddressBookProto.AddressBook.parseFrom(response.getEntity().getContent());
             addressBookVO = ProtoBeanUtils.toPojoBean(AddressBookVO.class, resp);
            System.out.println(addressBookVO);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
        return addressBookVO;

    }






}
