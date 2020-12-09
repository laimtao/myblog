package com.lmt.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmt.domain.Blog;
import com.lmt.domain.Type;
import com.lmt.service.BlogService;
import com.lmt.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/types")
    public String types(@RequestParam(name = "id") long id, @RequestParam(defaultValue = "1") int pageNum, Model model){
        PageHelper.startPage(pageNum,10);
        List<Type> types=typeService.getBlogType();
        if (id==-1){
            id=types.get(0).getId();
        }
        List<Blog> blogs=blogService.getBlogsByTypeId(id);
        PageInfo<Blog> pageInfo=new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("types",types);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
