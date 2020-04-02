package com.cy.blog.service;

import com.cy.blog.dao.CommentRepository;
import com.cy.blog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: 张文杰
 * Date: 2020/3/26
 * Time: 22:21
 */
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    /**
     * 查找评论数据
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    /**
     * 评论保存
     * @param comment
     * @return
     */
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1) {
            comment.setParentComment(commentRepository.findOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    // 存储子代信息
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 合并子评论节点
     * @param comments
     */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                //找出子代放到tempReplys中
                recursively(reply1);
            }
            //修改d顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清楚临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    /**
     * 递归迭代找出子代
      * @param comment
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点存放到临时存放集合
        if(comment.getReplyComments().size() > 0) {
            List<Comment> relpys = comment.getReplyComments();
            for (Comment reply : relpys) {
                tempReplys.add(reply);
                if(reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }


}
