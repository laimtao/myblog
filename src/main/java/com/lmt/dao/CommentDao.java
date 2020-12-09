package com.lmt.dao;

import com.lmt.domain.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {
    @Insert("insert into t_comment (nickname,content,avatar,\n" +
            "        create_time,blog_id,parent_comment_id, admincomment)\n" +
            "        values (#{nickname},#{content},#{avatar},\n" +
            "        #{createTime},#{blogId},#{parentCommentId}, #{adminComment});")
    @Results({
            @Result(column = "blog_id", property = "blogId"),
            @Result(column = "parent_comment_id", property = "parentCommentId"),
            @Result(column = "create_time", property = "createTime")
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveComment(Comment comment);

    @Select("select c.id,c.nickname,c.content,c.avatar,\n" +
            "        c.create_time,c.blog_id,c.parent_comment_id\n" +
            "        from t_comment c, t_blog b\n" +
            "        where c.blog_id = b.id and c.blog_id = #{blogId} and c.parent_comment_id = -1\n" +
            "        order by c.create_time desc")
    @Results({
            @Result(column = "blog_id", property = "blogId"),
            @Result(column = "parent_comment_id", property = "parentCommentId"),
            @Result(column = "create_time", property = "createTime")
    })
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId);

    @Select("select * from t_comment where id=#{parentCommentId}")
    @Results({
            @Result(column = "blog_id", property = "blogId"),
            @Result(column = "parent_comment_id", property = "parentCommentId"),
            @Result(column = "create_time", property = "createTime")
    })
    Comment findByParentCommentId(Long parentCommentId);

    @Select("select * from t_comment where parent_comment_id=#{parentCommentId}")
    @Results({
            @Result(column = "blog_id", property = "blogId"),
            @Result(column = "parent_comment_id", property = "parentCommentId"),
            @Result(column = "create_time", property = "createTime")
    })
    List<Comment> findReplyCommentsByParentId(Long parentCommentId);
}
