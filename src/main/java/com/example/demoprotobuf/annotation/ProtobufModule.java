package com.example.demoprotobuf.annotation;

import com.example.demoprotobuf.utils.JsonResult;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.springframework.beans.factory.support.BeanNameGenerator;

import java.lang.annotation.*;

/**
 * class desc
 * todo
 *
 * @author ykf
 * @date 2021/5/8 10:30
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProtobufModule {
    /** 默*/
    Class<? extends GeneratedMessageV3> proToBean() default GeneratedMessageV3.class;

    Class pojoBean() default Object.class;

    /** 默认值是否必填 **/
    boolean required() default false;
    /** 是否打印日志 **/
    boolean priLog() default false;
}
