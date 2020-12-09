package com.lmt.dao;

import com.lmt.domain.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TypeDao {
    @Select("select * from t_type")
    List<Type> getAllTypes();
    @Select("select * from t_type where id=#{id}")
    Type getTypeById(Long id);
    @Select("select * from t_type where name=#{name}")
    Type findTypeByName(String name);
    @Insert("insert into t_type values(#{id},#{name})")
    void save(Type type);
    @Delete("delete from t_type where id=#{id}")
    void deleteType(Long id);
    @Update("update t_type set name=#{name} where id=#{id}")
    void update(Type type);

    List<Type> getBlogType();
}
