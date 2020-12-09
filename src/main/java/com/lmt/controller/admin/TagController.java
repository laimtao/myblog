package com.lmt.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmt.domain.Tag;
import com.lmt.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    public String tags(@RequestParam(defaultValue = "1") int pagenum, Model model){
        PageHelper.startPage(pagenum,5);
        List<Tag> list=tagService.getAllTags();
        PageInfo<Tag> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String addTag(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }
    @GetMapping("/tags/{id}/input")
    public String update(@PathVariable Long id,Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }
    @PostMapping("/tags/add")
    public String toAddTag(Tag tag, RedirectAttributes attributes){
        Tag t=tagService.findTagByName(tag.getName());
        if(t!=null){
            attributes.addFlashAttribute("msg","不能添加重复的标签");
            return "redirect:/admin/tags/input";
        }else{
            attributes.addFlashAttribute("msg","添加成功");
        }
        tagService.addTag(tag);
        return "redirect:/admin/tags";
    }
    @PostMapping("/tags/update")
    public String toUpdateTag(Tag tag, RedirectAttributes attributes){
        Tag t=tagService.findTagByName(tag.getName());
        if(t!=null){
            attributes.addFlashAttribute("msg","不能添加重复的标签");
            return "redirect:/admin/tags/input";
        }else{
            attributes.addFlashAttribute("msg","修改成功");
        }
        tagService.updateTag(tag);
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg","删除成功");
        return "redirect:/admin/tags";
    }
}
