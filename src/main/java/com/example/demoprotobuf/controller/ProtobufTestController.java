package com.example.demoprotobuf.controller;
import com.example.demoprotobuf.annotation.ProtobufBodyModule;
import com.example.demoprotobuf.annotation.ProtobufModule;
import com.example.demoprotobuf.entry.AddressBookVO;
import com.example.demoprotobuf.protoc.AddressBookProto;
import com.example.demoprotobuf.utils.ProtoBeanUtils;
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

    private AddressBookVO addressBookVO;




    @PostMapping(value="/demo/test",produces = "application/x-protobuf")
    @ProtobufBodyModule(proToBean =AddressBookProto.AddressBook.class )
    public    AddressBookVO getPersonProto(@ProtobufModule(proToBean =AddressBookProto.AddressBook.class,
            pojoBean =AddressBookVO.class ) AddressBookVO addressBookVO) throws IOException {
        addressBookVO = ProtoBeanUtils.toPojoBean(AddressBookVO.class, getBuilder().build());
        return addressBookVO;

    }


    @PostMapping(value = "/demo/param")
    public   AddressBookVO   param() throws IOException, URISyntaxException {
        AddressBookProto.AddressBook.Builder builder = getBuilder();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URI uri = new URI("http", null, "127.0.0.1", 9988, "/protobuf/demo/test", "", null);
        HttpPost post = new HttpPost(uri);
        post.setEntity(new ByteArrayEntity(builder.build().toByteArray()));
        post.setHeader("Content-Type", "application/x-protobuf");
        HttpResponse response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();
        System.out.println(entity);
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

    private AddressBookProto.AddressBook.Builder getBuilder() {
        AddressBookProto.Person.Builder personBuilder = AddressBookProto.Person.newBuilder();

        AddressBookProto.Person.PhoneNumber.Builder phoneNumber = AddressBookProto.Person.PhoneNumber.newBuilder();

        phoneNumber.setNumber("1222");
        phoneNumber.setType( AddressBookProto.Person.PhoneType.MOBILE);

        personBuilder.setEmail("11").setId(11).setName("mihi").addPhones(phoneNumber);

        AddressBookProto.AddressBook.Builder builder = AddressBookProto.AddressBook.newBuilder();
        builder.addPeople(personBuilder.build());
        return builder;
    }


}
