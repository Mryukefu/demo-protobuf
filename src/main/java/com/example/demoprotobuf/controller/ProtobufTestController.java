package com.example.demoprotobuf.controller;
import com.example.demoprotobuf.entry.AddressBookVO;
import com.example.demoprotobuf.protoc.AddressBookProto;
import com.example.demoprotobuf.protoc.SearchRequestProto;
import com.example.demoprotobuf.service.ProtobufTestService;
import com.example.demoprotobuf.utils.ProtobufUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
        System.out.println(addressBookVO);
        return addressBookVO;

    }

    @GetMapping(value = "/demo/test2")
    public   AddressBookVO   getPersonProto2() {
        SearchRequestProto.SearchRequest.Builder searchRequestBuilder = SearchRequestProto.SearchRequest.newBuilder();
        searchRequestBuilder.setQuery("111");
        searchRequestBuilder.setPageNumber("2222");
        searchRequestBuilder.setResultPerPage("33333");

        AddressBookVO addressBookVO = ProtobufUtils.fromProtoBuffer(searchRequestBuilder.build(), AddressBookVO.class);
        System.out.println(addressBookVO);
        return addressBookVO;

    }

}
