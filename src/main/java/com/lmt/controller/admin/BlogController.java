package com.lmt.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmt.domain.*;
import com.lmt.service.BlogService;
import com.lmt.service.TagService;
import com.lmt.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    //回显所有类型和标签到页面，用来选择
    public void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.getAllTypes());
        model.addAttribute("tags",tagService.getAllTags());
    }
    @RequestMapping("/blogs")
    public String Blogs(Blog blog,@RequestParam(defaultValue = "1") int pagenum,Model model){
        PageHelper.startPage(pagenum,5);
        List<Blog> list=blogService.getAllBlogs(blog);//显示所有博客
        PageInfo<Blog> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("blog",blog);
        setTypeAndTag(model);
        return "admin/blogs";
    }
    @GetMapping("/blogs/input")
    public String addBlog(Model model){
        model.addAttribute("blog",new Blog());
        setTypeAndTag(model);
        return "admin/blogs-input";
    }
    @GetMapping("/blogs/{id}/input")
    public String updateBlog(@PathVariable Long id, Model model){
        Blog blog=blogService.findBlogById(id);//回显一条博客数据
        blog.setTagIds(tagService.findTagByBlogId(id));//将拼接好的标签字符串回显到页面
        blog.setTypeId(blogService.findTypeById(id));//将类型回显到页面
        model.addAttribute("blog",blog);
        setTypeAndTag(model);
        return "admin/blogs-input";
    }
    @PostMapping("/blogs/update")
    public String inputBlog(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setUserId(blog.getUser().getId());//将用户id存入数据库
//        blog.setType(typeService.getType(blog.getTypeId()));//得到页面的typeId，获得Type对象
        blog.setTags(tagService.getTagByString(blog.getTagIds()));//通过拼接的标签字符串获取List<Tag>，以便存入关联表
        if (blog.getId()==null){
            blogService.save(blog);
        }else{
            blogService.update(blog);
        }
        attributes.addFlashAttribute("msg","新增成功");
        return "redirect:/admin/blogs";
    }
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg","删除成功");
        return "redirect:/admin/blogs";
    }
}
