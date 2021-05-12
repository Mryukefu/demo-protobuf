package com.example.demoprotobuf.config;

import com.example.demoprotobuf.surport.ProtobufMethodArgumentResolver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitializingAdvice implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList(returnValueHandlers);
        List<HandlerMethodReturnValueHandler> handlers1 = new ArrayList<>();
        handlers1.add(new ProtobufMethodArgumentResolver());
        handlers1.addAll(handlers);
        adapter.setReturnValueHandlers(handlers1);

    }



}