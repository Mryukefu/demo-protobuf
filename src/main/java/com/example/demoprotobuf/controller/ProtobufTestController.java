package com.example.demoprotobuf.controller;
import com.example.demoprotobuf.entry.AddressBookVO;
import com.example.demoprotobuf.protoc.AddressBookProto;
import com.example.demoprotobuf.service.ProtobufTestService;
import com.example.demoprotobuf.utils.ProtobufUtils;
import com.google.protobuf.Descriptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    @Autowired
    private ProtobufTestService protobufTestService;

    @GetMapping(value = "/demo/test")
    public   AddressBookVO   getPersonProto() {
      AddressBookProto.Person.Builder personBuilder = AddressBookProto.Person.newBuilder();

        AddressBookProto.Person.PhoneNumber.Builder phoneNumber = AddressBookProto.Person.PhoneNumber.newBuilder();

        phoneNumber.setNumber("1222");
        phoneNumber.setType( AddressBookProto.Person.PhoneType.MOBILE);

        personBuilder.setEmail("11").setId(11).setName("mihi").addPhones(phoneNumber);

        AddressBookProto.AddressBook.Builder builder = AddressBookProto.AddressBook.newBuilder();

        builder.addPeople(personBuilder.build());

        AddressBookVO addressBookVO = ProtobufUtils.fromProtoBuffer(builder.build(), AddressBookVO.class);
        //System.out.println(addressBookVO);
        return addressBookVO;

    }

}
