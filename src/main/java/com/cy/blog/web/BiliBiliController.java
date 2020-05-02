package com.cy.blog.web;

import com.cy.blog.util.UploadUtil;
import com.cy.blog.vo.AjaxJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: 张文杰
 * Date: 2020/4/4
 * Time: 7:50
 */
@Controller
public class BiliBiliController {

    @RequestMapping("/bilibili")
    public String bilibili() {
        return "bilibili";
    }

    @RequestMapping("/bilibili/file")
    @ResponseBody
    public AjaxJson imgUp(String img, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson aj = new AjaxJson();
        aj = UploadUtil.saveToImgByStr(img);
        return aj;
    }
}
