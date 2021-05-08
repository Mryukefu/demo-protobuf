package com.example.demoprotobuf.surport;

import com.example.demoprotobuf.annotation.ProtobufBodyModule;
import com.example.demoprotobuf.annotation.ProtobufModule;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * class desc
 * todo
 *
 * @author ykf
 * @date 2021/5/8 18:58
 */
public class PojoToProtobufBeanMethodArgumentResolver implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasParameterAnnotation(ProtobufBodyModule.class);
    }

    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter returnType,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        System.out.println("ddddd");
    }
}
