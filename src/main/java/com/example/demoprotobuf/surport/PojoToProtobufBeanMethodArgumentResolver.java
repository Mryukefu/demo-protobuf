package com.example.demoprotobuf.surport;

import com.example.demoprotobuf.annotation.ProtobufBodyModule;
import com.example.demoprotobuf.utils.ProtoBeanUtils;
import com.google.protobuf.GeneratedMessageV3;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;

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
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ProtobufBodyModule.class) ||
                returnType.hasMethodAnnotation(ProtobufBodyModule.class));
    }

    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter returnType,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {

        mavContainer.setRequestHandled(true);
        ProtobufBodyModule methodAnnotation = returnType.getMethodAnnotation(ProtobufBodyModule.class);
        if (methodAnnotation!=null){
            Class<? extends GeneratedMessageV3> aClass = methodAnnotation.proToBean();
            GeneratedMessageV3.Builder newBuilder = (GeneratedMessageV3.Builder)aClass.getMethod("newBuilder").invoke(aClass);
            ProtoBeanUtils.toProtoBean(newBuilder,returnValue);
            byte[] bytes = newBuilder.build().toByteArray();
            webRequest
                    .getNativeResponse(HttpServletResponse.class)
                    .getOutputStream()
                    .write(bytes);
        }
    }
}
