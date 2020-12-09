package com.lmt.dao;

import com.lmt.domain.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDao {
    @Select("select * from t_tag where name=#{name}")
    Tag findTagByName(String name);
    @Select("select * from t_tag")
    List<Tag> getAllTags();
    @Select("select * from t_tag where id=#{id}")
    Tag getTagById(Long id);
    @Insert("insert into t_tag values(#{id},#{name})")
    void addTag(Tag tag);
    @Update("update t_tag set name=#{name} where id=#{id}")
    void updateTag(Tag tag);
    @Delete("delete from t_tag where id=#{id}")
    void deleteTag(Long id);
    @Select("select tag_id from t_blog_tags where blog_id=#{id}")
    List<Integer> findTagByBlogId(Long id);
    List<Tag> getBlogTags();
}
