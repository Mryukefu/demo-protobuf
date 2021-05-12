package com.example.demoprotobuf.exception;

import com.example.demoprotobuf.annotation.ProtobufResponseModule;
import com.example.demoprotobuf.entry.contanst.LogEnum;
import com.example.demoprotobuf.entry.contanst.ResultEnum;
import com.example.demoprotobuf.protoc.PersonResultProto;
import com.example.demoprotobuf.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * MyExceptionHandler class
 * 全局异常处理器
 *
 * @author : wgn
 * @date : 2020/5/13
 */
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ProtobufResponseModule(proToBean = PersonResultProto.PersonResult.class,priLog = LogEnum.INFO)
    @ResponseBody
    public JsonResult handleIllegalParamException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append("!"));
        }
        return new JsonResult<>(ResultEnum.REQUEST_PARAMETER_ERROR, errorMsg.toString());
    }

    @ExceptionHandler(BusinessException.class)
    @ProtobufResponseModule(proToBean = PersonResultProto.PersonResult.class,priLog = LogEnum.INFO)
    @ResponseBody
    public JsonResult handleBusinessException(BusinessException e) {
        JsonResult result = new JsonResult();
        result.setCode(e.getErrCode());
        result.setMessage(e.getMessage());
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ProtobufResponseModule(proToBean = PersonResultProto.PersonResult.class,priLog = LogEnum.INFO)
    public JsonResult exception(Exception e) {
        log.error("[请求失败]",e);
     return new JsonResult(ResultEnum.FAIL);
    }
}
