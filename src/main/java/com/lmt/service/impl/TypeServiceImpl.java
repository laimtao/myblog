package com.lmt.service.impl;

import com.github.pagehelper.PageHelper;
import com.lmt.dao.TypeDao;
import com.lmt.domain.Type;
import com.lmt.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeDao typeDao;
    @Override
    public List<Type> getAllTypes() {
        return typeDao.getAllTypes();
    }

    @Override
    public Type getType(Long id) {
        return typeDao.getTypeById(id);
    }

    @Override
    public Type findTypeByName(String name) {
        return typeDao.findTypeByName(name);
    }

    @Override
    public void save(Type type) {
        typeDao.save(type);
    }

    @Override
    public void deleteType(Long id) {
        typeDao.deleteType(id);
    }

    @Override
    public void update(Type type) {
        typeDao.update(type);
    }

    @Override
    public List<Type> getBlogType() {
        return typeDao.getBlogType();
    }
}
