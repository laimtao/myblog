package com.lmt.service;

import com.lmt.domain.Type;

import java.util.List;

public interface TypeService {
    List<Type> getAllTypes();

    Type getType(Long id);

    Type findTypeByName(String name);

    void save(Type type);

    void deleteType(Long id);

    void update(Type type);

    List<Type> getBlogType();

}
