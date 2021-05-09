package com.example.demoprotobuf.surport;
import com.example.demoprotobuf.annotation.ProtobufModule;
import com.example.demoprotobuf.protoc.PersonResultProto;
import com.example.demoprotobuf.utils.JsonResult;
import com.example.demoprotobuf.utils.ProtoBeanUtils;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * class desc
 *  将ProtoBean对象转化为POJO对象
 * @author ykf
 * @date 2021/5/8 10:36
 */
public class ProtobufBeanToPojoMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     *
     *  是否支持这个类型的参数解析
     * @param methodParameter  方法包装类
     * @return {@code boolean}
     * @author ykf
     * @date 2021/5/8 10:40
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ProtobufModule.class);
    }

    /**
     *
     * 进行参数解析
     * @param parameter  方法包装类
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
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            int len = request.getContentLength();
            ServletInputStream iii = request.getInputStream();
            byte[] buffer = new byte[len];
            iii.read(buffer, 0, len);
            System.out.println("出参数");
            for (byte aByte : buffer) {
                System.out.print(aByte);
            }
            ProtobufModule ann = parameter.getParameterAnnotation(ProtobufModule.class);
            Class<? extends GeneratedMessageV3> aClass = ann.proToBean();

            Method parseFrom = aClass.getMethod("parseFrom", byte[].class);
            GeneratedMessageV3 message = (GeneratedMessageV3)parseFrom.invoke(aClass, buffer);
            return ProtoBeanUtils.toPojoBean(ann.pojoBean(), message);

    }
}
