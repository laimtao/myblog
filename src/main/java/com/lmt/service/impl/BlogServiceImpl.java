package com.lmt.service.impl;

import com.lmt.dao.BlogDao;
import com.lmt.domain.Blog;
import com.lmt.domain.BlogAndTag;
import com.lmt.domain.Tag;
import com.lmt.exception.NotFoundException;
import com.lmt.service.BlogService;
import com.lmt.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;
    @Override
    public List<Blog> getAllBlogs(Blog blog) {
        return blogDao.getAllBlogs(blog);
    }

    @Override
    public Blog findBlogById(Long id) {
        return blogDao.findBlogById(id);
    }

    @Override
    public void save(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blogDao.saveBlog(blog);
        List<Tag> tags=blog.getTags();
        for (Tag tag:tags){
            BlogAndTag blogAndTag=new BlogAndTag(blog.getId(),tag.getId());
            blogDao.saveBlogAndTag(blogAndTag);//将数据存入关联表
        }
    }

    @Override
    public void update(Blog blog) {
        blog.setUpdateTime(new Date());
        List<Tag> tags=blog.getTags();
        blogDao.updateBlog(blog);
        blogDao.deleteBlogAndTag(blog.getId());//更新前先删除
        for (Tag tag:tags){
            BlogAndTag blogAndTag=new BlogAndTag(blog.getId(),tag.getId());
            blogDao.saveBlogAndTag(blogAndTag);
        }
    }

    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteBlogAndTag(id);//先删除关联表
        blogDao.deleteBlog(id);
    }

    @Override
    public Long findTypeById(Long id) {
        return blogDao.findTypeById(id);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years=blogDao.findGroupYear();//获取所有的年份,去掉日期
        Set<String> set=new HashSet<>(years);//去掉重复的年份
        Map<String ,List<Blog>> map=new HashMap<>();
        for (String year:set){
            map.put(year,blogDao.findByYear(year));//将每一年的所有博客存入map中
        }
        return map;
    }

    @Override
    public int blogCount() {
        return blogDao.blogCount();
    }

    @Override
    public List<Blog> getBlogsByTagId(long id) {
        return blogDao.getBlogByTagId(id);
    }

    @Override
    public List<Blog> getBlogsByTypeId(long id) {
        return blogDao.getBlogsByTypeId(id);
    }

    @Override
    public List<Blog> getBlogs(String query) {
        return blogDao.getBlogs(query);
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return blogDao.getAllRecommendBlog();
    }

    @Override
    public Blog getDetailBlog(Long id) {
        Blog blog= blogDao.getDetailBlog(id);//获取博客详情，多表查询
        if (blog==null){
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));//将markdown转换为html
        return blog;
    }

    @Override
    public void saveViewCount(Long id) {
        blogDao.saveViews(id);
    }
}
