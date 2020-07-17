package com.cy.blog.service;

import com.cy.blog.dao.UserRepository;
import com.cy.blog.po.User;
import com.cy.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: 张文杰
 * Date: 2020/2/12
 * Time: 11:12
 * Description: 用户登陆业务实现层
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
