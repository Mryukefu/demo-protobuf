package com.example.demoprotobuf.utils;

import com.example.demoprotobuf.protoc.AddressBookProto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * class desc
 * todo
 *
 * @author ykf
 * @date 2021/5/7 18:22
 */

public class ProtobufUtils {


    public static  <T> T fromProtoBuffer(AddressBookProto.AddressBook addressBook,Class<T> pojo )  {
          T t= null;
        try {
            t = pojo.newInstance();
            Field[] modelFields = pojo.getDeclaredFields();
            if (modelFields!=null&&modelFields.length>0){
                for (Field modelField : modelFields) {
                    String fieldName = modelField.getName();

                    String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class fieldType = modelField.getType();
                    Class<?> type = modelField.getType();
                    String methodName = "get" + name;
                    String name1 = type.getName();
                    System.out.println(name1);
                    if (name1.equals("java.util.List")){
                        methodName = methodName+"List";
                    }
                    addressBook.getPeopleList();
                    Method pbGetMethod = addressBook.getClass().getMethod(methodName);

                    Object value = pbGetMethod.invoke(addressBook);

                    Method modelSetMethod = pojo.getMethod("set" + name, fieldType);

                    modelSetMethod.invoke(t, value);
                }
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return t;

    }
}
