package com.lmt.dao;

import com.lmt.domain.Blog;
import com.lmt.domain.BlogAndTag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogDao {
    /*@Select("select * from t_blog")
    @Results({
            @Result(column = "update_time",property = "updateTime"),
            @Result(column = "type_id", property = "type", one = @One(select = "com.lmt.dao.TypeDao.getTypeById"))
    })*/
    @SelectProvider(type=com.lmt.dao.Provider.class,method="select")
    @Results({
            @Result(column = "update_time",property = "updateTime"),
            @Result(column = "type_id", property = "type", one = @One(select = "com.lmt.dao.TypeDao.getTypeById"))
    })
    List<Blog> getAllBlogs(Blog blog);

    @Select("select * from t_blog where id=#{id}")
    /*@Results({
            @Result(column = "update_time",property = "updateTime"),
            @Result(column = "first_picture", property = "firstPicture"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "share_statement",property = "shareStatement"),
            @Result(column = "type_id",property = "typeId")
    })*/
    Blog findBlogById(Long id);

    @Insert("insert into t_blog(title,content,first_picture,flag,views,appreciation,share_statement,commentabled,published,recommend, create_time,update_time, type_id, user_id, description) values(#{title}, #{content}, #{firstPicture}, #{flag}, #{views}, #{appreciation},#{shareStatement}, #{commentabled}, #{published}, #{recommend}, #{createTime},#{updateTime}, #{typeId}, #{userId}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveBlog(Blog blog);

    @Insert("insert into t_blog_tags(blog_id,tag_id) values(#{blogId},#{tagId})")
    void saveBlogAndTag(BlogAndTag blogAndTag);

    @Delete("delete from t_blog where id=#{id}")
    void deleteBlog(Long id);
    @Select("select type_id from t_blog where id=#{id}")
    Long findTypeById(Long id);
    @Delete("delete from t_blog_tags where blog_id=#{id}")
    void deleteBlogAndTag(Long id);
    @Select("select count(*) from t_blog")
    int blogCount();
    @Select("select DATE_FORMAT(update_time, '%Y') from t_blog order by update_time desc")
    List<String> findGroupYear();
    /*@Results({
            @Result(column = "update_time",property = "updateTime"),
            @Result(column = "first_picture", property = "firstPicture"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "share_statement",property = "shareStatement"),
            @Result(column = "type_id",property = "typeId")
    })*/
    @Select("select * from t_blog where DATE_FORMAT(update_time, '%Y') = #{year}")
    List<Blog> findByYear(String year);

    List<Blog> getBlogByTagId(long id);

    List<Blog> getBlogsByTypeId(long id);

    List<Blog> getBlogs(String query);
    @Select("select * from t_blog where recommend=1 order by update_time desc")
    List<Blog> getAllRecommendBlog();

    Blog getDetailBlog(Long id);
    @Update("update t_blog set views=views+1 where id=#{id}")
    void saveViews(Long id);

    void updateBlog(Blog blog);
}
