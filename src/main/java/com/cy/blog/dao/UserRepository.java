package com.cy.blog.dao;

import com.cy.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: 张文杰
 * Date: 2020/2/12
 * Time: 11:14
 * 用户登陆Dao层//JPA与数据库交互
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username, String password);
}
