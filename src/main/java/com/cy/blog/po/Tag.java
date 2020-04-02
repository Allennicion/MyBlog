package com.cy.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: 张文杰
 * Date: 2020/2/11
 * Time: 11:17
 * 标签
 */
@Entity //jpa 标识实体类
@Table(name  = "t_tag") //映射数据库
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
