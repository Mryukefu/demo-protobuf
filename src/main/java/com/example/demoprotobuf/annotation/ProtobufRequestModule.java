package com.example.demoprotobuf.annotation;

import com.example.demoprotobuf.entry.contanst.LogEnum;
import com.google.protobuf.GeneratedMessageV3;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /** java 方法参数的实体类类 非必须*/
    Class paramPojoBeanClass() default Object.class;

    /** 是否打印日志 日志级别1 - log,2 - debug,3 --err **/
    LogEnum priLog() default LogEnum.INFO;

    /** 字节流流转 GeneratedMessageV3 **/
    String parseFromMethod() default "parseFrom";

}
