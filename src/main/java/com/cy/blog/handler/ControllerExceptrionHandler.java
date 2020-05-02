package com.cy.blog.handler;

import com.cy.blog.vo.AjaxJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * User: 张文杰
 * Date: 2020/2/12
 * Time: 11:12
 * Description: 异常处理拦截
 */
@ControllerAdvice
public class ControllerExceptrionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxJson execptionHandler(HttpServletRequest request, Exception e) throws Exception {
        AjaxJson aj = new AjaxJson();
        logger.error("Request URL : {}, Exception : {}", request.getRequestURL(),e);
        /*这里进行判断,是否是自己定义的异常处理,如果是则这里不做拦截处理*/
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            /*将异常抛出让springboot进行处理*/
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        aj.setStatus(201);
        return aj;
    }
}
