package com.example.demoprotobuf.surport;

import com.example.demoprotobuf.annotation.ProtobufRequestModule;
import com.example.demoprotobuf.utils.ProtoBeanUtils;
import com.google.protobuf.GeneratedMessageV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * class desc
 * protobuf 请求参数转换类
 * @author ykf
 * @date 2021/5/8 10:36
 */
@Slf4j
public class ProtobufBeanToPojoMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 是否支持这个类型的参数解析
     *
     * @param methodParameter 方法包装类
     * @return {@code boolean}
     * @author ykf
     * @date 2021/5/8 10:40
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ProtobufRequestModule.class);
    }

    /**
     * 进行参数解析
     *
     * @param parameter             方法包装类
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return {@code java.lang.Object}
     * @author ykf
     * @date 2021/5/8 10:40
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        //  如果定义得了转换注解的话
        ProtobufRequestModule ann = parameter.getParameterAnnotation(ProtobufRequestModule.class);
        if (ann == null) {
            return null;
        }
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (!request.getMethod().equals("POST")) {
            log.error("[protobuf请求方式错误]只执行post请求");
            return request.getInputStream();
        }
        int len = request.getContentLength();
        ServletInputStream stream = request.getInputStream();
        byte[] buffer = new byte[len];
        stream.read(buffer, 0, len);
        Class<? extends GeneratedMessageV3> messageClass = ann.proToBeanClass();
        Method parseFrom = messageClass.getMethod(ann.parseFromMethod(), byte[].class);
        GeneratedMessageV3 message = (GeneratedMessageV3) parseFrom.invoke(messageClass, buffer);
        Object param = ProtoBeanUtils.toPojoBean(ann.paramPojoBeanClass(), message);
        priLog(ann.priLog(), request,parameter.getMethod(), param);
        return param;
    }

    public void priLog(Integer priLog, HttpServletRequest request,Method method, Object param) {
        if (priLog == null || priLog <= 0 || priLog >= 4) {
            return;
        }
        if (priLog == 1) {
            log.info("【proto入参】[请求方法路径]{},[请求方法类型]{}，[请求方法名称]{}，[请求参数]{}", request.getServletPath(),
                    request.getMethod(),method.getDeclaringClass().getName()+"."+method.getName(), param);
            return;
        }
        if (priLog == 2) {
            log.info("【proto入参】[请求方法路径]{},[请求方法类型]{}，[请求方法名称]{}，[请求参数]{}", request.getServletPath(),
                    request.getMethod(),method.getDeclaringClass().getName()+"."+method.getName(), param);
            return;
        }
        if (priLog == 3) {
            log.info("【proto入参】[请求方法路径]{},[请求方法类型]{}，[请求方法名称]{}，[请求参数]{}", request.getServletPath(),
                    request.getMethod(),method.getDeclaringClass().getName()+"."+method.getName(), param);
            return;
        }

    }
}
