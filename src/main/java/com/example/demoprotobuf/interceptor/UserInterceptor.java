package com.example.demoprotobuf.interceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ykf
 * @version 1.0.0
 * @Description 用户拦截器，根据token查找用户,放入线程变量
 * @createTime 2020年01月03日 16:25:00
 */
@Slf4j
@Component
public class UserInterceptor implements AsyncHandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {


        }


}
