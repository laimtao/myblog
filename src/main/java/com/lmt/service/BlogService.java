package com.lmt.service;

import com.lmt.domain.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    List<Blog> getAllBlogs(Blog blog);

    Blog findBlogById(Long id);

    void save(Blog blog);

    void update(Blog blog);

    void deleteBlog(Long id);

    Long findTypeById(Long id);

    Map<String,List<Blog>> archiveBlog();

    int blogCount();

    List<Blog> getBlogsByTagId(long id);

    List<Blog> getBlogsByTypeId(long id);

    List<Blog> getBlogs(String query);

    List<Blog> getAllRecommendBlog();

    Blog getDetailBlog(Long id);

    void saveViewCount(Long id);
}
