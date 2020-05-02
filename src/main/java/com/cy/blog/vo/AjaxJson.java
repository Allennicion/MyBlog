package com.cy.blog.vo;

import java.util.Map;

/**
 * User: 张文杰
 * Date: 2020/4/4
 * Time: 10:32
 */
public class AjaxJson {

    private Integer status;	//定义状态码 200-成功 201-失败
    private String msg; //服务器返回客户端消息
    private Object data;	//服务器返回客户端数据

    public AjaxJson() {
    }

    public AjaxJson(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static AjaxJson fromObject(Map<String, Object> map) {
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static AjaxJson success() {
        return new AjaxJson(200, null, null);
    }
    public static AjaxJson success(Object object) {
        return new AjaxJson(200, null, object);
    }
    public static AjaxJson success(String msg,Object object) {
        return new AjaxJson(200, msg, object);
    }
    public static AjaxJson fail() {
        return new AjaxJson(201, null, null);
    }
    public static AjaxJson fail(String msg) {
        return new AjaxJson(201, msg, null);
    }
}
