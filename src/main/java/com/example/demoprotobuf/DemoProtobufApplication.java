package com.example.demoprotobuf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

@SpringBootApplication
public class DemoProtobufApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoProtobufApplication.class, args);
        System.out.println();
    }

}
