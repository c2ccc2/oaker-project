package com.oaker.web.core.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @Description : 错误页面配置错误页面配置
 * <功能详细描述>
 * @author: 须尽欢_____
 * @Data : 2021/10/27 9:49
 */
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        /*1、按错误的类型显示错误的网页*/
        /*错误类型为404，找不到网页的，默认显示404.html网页*/
        ErrorPage e404 = new ErrorPage(HttpStatus.NOT_FOUND, "/static/404.html");
        /*错误类型为500，表示服务器响应错误，默认显示500.html网页*/
        /*ErrorPage e500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/static/error/index.jsp");
        ErrorPage e400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/static/error/index.jsp");*/
        registry.addErrorPages(e404);
    }

}
