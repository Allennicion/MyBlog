package com.cy.blog.dao;

import com.cy.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * User: 张文杰
 * Date: 2020/3/8
 * Time: 21:12
 * 新增类型Dao
 */
public interface TypesRepository extends JpaRepository<Type,Long> {
    /**
     * 通过类型名称获取
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
