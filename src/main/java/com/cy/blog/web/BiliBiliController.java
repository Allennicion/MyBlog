package com.cy.blog.web;

import com.cy.blog.vo.AjaxJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

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
    public AjaxJson imgUp(File file) {
        AjaxJson aj = new AjaxJson();
        System.out.println(file.toString());
        return aj;
    }
}
