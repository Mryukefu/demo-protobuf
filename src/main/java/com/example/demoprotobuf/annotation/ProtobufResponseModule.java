package com.example.demoprotobuf.annotation;

import com.google.protobuf.GeneratedMessageV3;

import java.lang.annotation.*;

/**
 * class desc
 * todo
 *
 * @author ykf
 * @date 2021/5/8 10:30
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProtobufResponseModule {
    /** 生成的java message类*/
    Class<? extends GeneratedMessageV3> proToBean() default GeneratedMessageV3.class;
    /** 是否打印日志 日志级别1 - log,2 - debug,3 --err **/
    int priLog() default 0;
}
