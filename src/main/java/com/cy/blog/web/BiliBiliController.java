package com.cy.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
