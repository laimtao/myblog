package com.lmt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmt.domain.Blog;
import com.lmt.domain.Tag;
import com.lmt.domain.Type;
import com.lmt.service.BlogService;
import com.lmt.service.TagService;
import com.lmt.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @RequestMapping("/")
    public String toIndex(@RequestParam(defaultValue = "1") int pageNum,String query, Model model){
        PageHelper.startPage(pageNum,6);
        List<Blog> blogs=blogService.getBlogs(query);
        List<Type> allTypes=typeService.getBlogType();
        List<Tag> allTags=tagService.getBlogTags();
        List<Blog> recommendBlog=blogService.getAllRecommendBlog();
        PageInfo<Blog> pageInfo=new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        if (query!=null){
            model.addAttribute("query", query);
            return "search";
        }else{
            model.addAttribute("tags",allTags);
            model.addAttribute("types",allTypes);
            model.addAttribute("recommendBlogs",recommendBlog);
            return "index";
        }
    }
    @GetMapping("/blog/{id}")
    public String toDetail(@PathVariable Long id, Model model){
        blogService.saveViewCount(id);
        Blog blog=blogService.getDetailBlog(id);
        model.addAttribute("blog",blog);
        return "blog";
    }
}
