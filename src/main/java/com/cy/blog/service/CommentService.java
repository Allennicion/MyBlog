package com.cy.blog.service;

import com.cy.blog.po.Comment;

import java.util.List;

/**
 * User: 张文杰
 * Date: 2020/3/26
 * Time: 22:20
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
