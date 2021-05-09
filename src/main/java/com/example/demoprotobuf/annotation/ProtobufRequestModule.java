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
public @interface ProtobufRequestModule {
    /**  生成的java message类 */
    Class<? extends GeneratedMessageV3> proToBeanClass() default GeneratedMessageV3.class;

    /** java 方法参数的实体类类 */
    Class paramPojoBeanClass() default Object.class;

    /** 是否打印日志 日志级别1 - log,2 - debug,3 --err **/
    int priLog() default 1;

    /** 字节流流转 GeneratedMessageV3 **/
    String parseFromMethod() default "parseFrom";

}
