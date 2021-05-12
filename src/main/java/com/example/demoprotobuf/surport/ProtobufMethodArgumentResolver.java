package com.example.demoprotobuf.surport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demoprotobuf.annotation.ProtobufRequestModule;
import com.example.demoprotobuf.annotation.ProtobufResponseModule;
import com.example.demoprotobuf.entry.contanst.LogEnum;
import com.example.demoprotobuf.utils.ProtoBeanUtils;
import com.google.gson.JsonObject;
import com.google.protobuf.GeneratedMessageV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * class desc
 * protobuf protobuf 参数转换类
 * @author ykf
 * @date 2021/5/8 10:36
 */
@Slf4j
public class ProtobufMethodArgumentResolver implements HandlerMethodArgumentResolver,
        HandlerMethodReturnValueHandler   {

    /**
     * 是否支持这个类型的参数解析
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
     * 请求参数解析
     * @param parameter  方法包装类
     * @param modelAndViewContainer 数据与视图
     * @param nativeWebRequest 请求对象
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
        Object param = ProtoBeanUtils.toPojoBean(parameter.getNestedParameterType(), message);
        priReqLog(ann.priLog(), request,parameter.getMethod(), param);
        return param;
    }

    public Object getParam(HttpServletRequest request,MethodParameter parameter) throws Exception {
        String jsonParam = request.getHeader("jsonParam");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonParam);
        Object param = JSONObject.toJavaObject(jsonObject, parameter.getNestedParameterType());
        return param;
    }


   /**
    *
    *  请求打印
    * @param logEnum  日志级别枚举
    * @param request 请求参数request
    * @param method  方法对象
    * @param param 请求参数
    * @return {@code void}
    * @author ykf
    * @date 2021/5/10 14:10
    */
    public void priReqLog(LogEnum logEnum, HttpServletRequest request, Method method, Object param) {
        if (logEnum.getLevel() == null || logEnum.getLevel() <= 0 || logEnum.getLevel() >= 4) {
            return;
        }
        if (logEnum.getLevel() == 1) {
            log.info("【proto入参】[请求方法路径]{},[请求方法类型]{}，[请求方法名称]{}，[请求参数]{}", request.getServletPath(),
                    request.getMethod(),method.getDeclaringClass().getName()+"."+method.getName(), param);
            return;
        }
        if (logEnum.getLevel() == 2) {
            log.debug("【proto入参】[请求方法路径]{},[请求方法类型]{}，[请求方法名称]{}，[请求参数]{}", request.getServletPath(),
                    request.getMethod(),method.getDeclaringClass().getName()+"."+method.getName(), param);
            return;
        }
        if (logEnum.getLevel() == 3) {
            log.error("【proto入参】[请求方法路径]{},[请求方法类型]{}，[请求方法名称]{}，[请求参数]{}", request.getServletPath(),
                    request.getMethod(),method.getDeclaringClass().getName()+"."+method.getName(), param);
            return;
        }

    }


    /**
     *
     * 返回参数解析
     * @param returnType
     * @return {@code boolean}
     * @author ykf
     * @date 2021/5/10 14:11
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
       return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ProtobufResponseModule.class) ||
                returnType.hasMethodAnnotation(ProtobufResponseModule.class));



    }

    /**
     *
     * 返回参数解析
     * @param returnValue 返回值
     * @param returnType  返回类型
     * @param mavContainer  module vie 容器
     * @param webRequest  请求参数
     * @return {@code void}
     * @author ykf
     * @date 2021/5/10 14:11
     */
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
        GeneratedMessageV3.Builder newBuilder = (GeneratedMessageV3.Builder) aClass.getMethod(methodAnnotation.builderMethod()).invoke(aClass);
        ProtoBeanUtils.toProtoBean(newBuilder, returnValue);
        byte[] bytes = newBuilder.build().toByteArray();
        priReTurnLog(methodAnnotation.priLog(), webRequest,returnType.getMethod(), returnValue);
        webRequest
                .getNativeResponse(HttpServletResponse.class)
                .getOutputStream()
                .write(bytes);
    }


    /**
     *
     *  返回打印
     * @param logEnum  日志级别枚举
     * @param webRequest 请求参数request
     * @param method  方法对象
     * @param param 请求参数
     * @return {@code void}
     * @author ykf
     * @date 2021/5/10 14:10
     */
    public void priReTurnLog(LogEnum logEnum, NativeWebRequest webRequest, Method method, Object param) {
        if (logEnum == null || logEnum.getLevel() <= 0 || logEnum.getLevel() >= 4) {
            return;
        }
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (logEnum.getLevel() == 1) {
            log.info("【proto返回参数】[返回方法路径]{},[返回方法类型]{}，[返回方法名称]{}，[返回结果]{}", request.getServletPath(),
                    request.getMethod(),method.getDeclaringClass().getName()+"."+method.getName(), param);
            return;
        }
        if (logEnum.getLevel() == 2) {
            log.debug("【proto返回参数】[返回方法路径]{},[返回方法类型]{}，[返回方法名称]{}，[返回结果]{}", request.getServletPath(),
                    request.getMethod(),method.getDeclaringClass().getName()+"."+method.getName(), param);
            return;
        }
        if (logEnum.getLevel() == 3) {
            log.error("【proto返回参数】[返回方法路径]{},[返回方法类型]{}，[返回方法名称]{}，[返回结果]{}", request.getServletPath(),
                    request.getMethod(),method.getDeclaringClass().getName()+"."+method.getName(), param);
            return;
        }
    }
}
