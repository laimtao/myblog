package com.lmt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmt.domain.Blog;
import com.lmt.domain.Tag;
import com.lmt.service.BlogService;
import com.lmt.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    public String tags(@RequestParam(name = "id") long id, @RequestParam(defaultValue = "1") int pageNum, Model model, HttpServletRequest request){
        PageHelper.startPage(pageNum,10);
        List<Tag> tags=tagService.getBlogTags();//获取页面上每一块需要展示的部分，存入Tag
        if (id==-1){
            id=tags.get(0).getId();//将id设置为Tag的第一个id
        }
        List<Blog> blogs=blogService.getBlogsByTagId(id);//获取一个标签的所有博客

        PageInfo<Blog> pageInfo=new PageInfo<>(blogs);
        model.addAttribute("tags",tags);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
