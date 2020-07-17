package com.cy.blog.service;

import com.cy.blog.NotFoundException;
import com.cy.blog.dao.TypesRepository;
import com.cy.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: 张文杰
 * Date: 2020/3/8
 * Time: 21:12
 * 新增类型实现类
 */
@Service
public class TypesServiceImpl implements TypesService {

    @Autowired
    private TypesRepository typesRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typesRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typesRepository.findOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typesRepository.getTypeByName(name);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typesRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typesRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return typesRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typesRepository.findOne(id);
        if (t == null) throw new NotFoundException("不存在该类型");
        BeanUtils.copyProperties(type, t);
        return typesRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typesRepository.delete(id);
    }
}
