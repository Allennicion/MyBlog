package com.cy.blog.service;

import com.cy.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: 张文杰
 * Date: 2020/3/9
 * Time: 21:03
 * 标签接口
 */
public interface TagService {

    Tag saveTag(Tag tag);

    Tag saveAndFlush(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
