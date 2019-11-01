package com.zjj.community.community.advice;

import com.alibaba.fastjson.JSON;
import com.zjj.community.community.dto.ResultDTO;
import com.zjj.community.community.exception.CustomizeErrorCode;
import com.zjj.community.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response){
        String contentType= request.getContentType();
        //判断错误类型
        if ("applisation/json".equals(contentType)){
            ResultDTO resultDTO;
            //返回json提示
            if (e instanceof CustomizeException){
                resultDTO= ResultDTO.errorOf((CustomizeException)e);
            }else {
                resultDTO= ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }else {
            //跳转错误页面
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
        return null;
    }

}
