package com.cy.blog.service;

import com.cy.blog.po.User;

/**
 * User: 张文杰
 * Date: 2020/2/19
 * Time: 9:54
 * Description: 用户登录接口
 */
public interface UserService {

    User checkUser(String username, String password);
}
