package com.example.demoprotobuf.surport;

import com.example.demoprotobuf.annotation.ProtobufResponseModule;
import com.example.demoprotobuf.utils.ProtoBeanUtils;
import com.google.protobuf.GeneratedMessageV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * class desc
 * protobuf 返回参数转换类
 * @author ykf
 * @date 2021/5/8 18:58
 */
@Slf4j
public class PojoToProtobufBeanMethodArgumentResolver implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ProtobufResponseModule.class) ||
                returnType.hasMethodAnnotation(ProtobufResponseModule.class));
    }

    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter returnType,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        ProtobufResponseModule methodAnnotation = returnType.getMethodAnnotation(ProtobufResponseModule.class);
        if (methodAnnotation==null){
            return;
        }
            Class<? extends GeneratedMessageV3> aClass = methodAnnotation.proToBean();
            GeneratedMessageV3.Builder newBuilder = (GeneratedMessageV3.Builder) aClass.getMethod("newBuilder").invoke(aClass);

            ProtoBeanUtils.toProtoBean(newBuilder, returnValue);
            byte[] bytes = newBuilder.build().toByteArray();
            priLog(methodAnnotation.priLog(), webRequest, returnValue);
            webRequest
                    .getNativeResponse(HttpServletResponse.class)
                    .getOutputStream()
                    .write(bytes);

    }

    public void priLog(Integer priLog, NativeWebRequest webRequest, Object param) {
        if (priLog == null || priLog <= 0 || priLog >= 4) {
            return;
        }
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (priLog == 1) {
            log.info("【返回方法路径】{},[返回方法名称]{}，[返回参数]{}", request.getPathInfo(), request.getMethod(), param);
            return;
        }
        if (priLog == 2) {
            log.debug("【返回方法路径】{},[返回方法名称]{}，[返回参数]{}", request.getPathInfo(), request.getMethod(), param);
            return;
        }
        if (priLog == 3) {
            log.error("【返回方法路径】{},[返回方法名称]{}，[返回参数]{}", request.getPathInfo(), request.getMethod(), param);
            return;
        }
    }

}
