package com.lmt.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmt.domain.Type;
import com.lmt.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "1") int pagenum, Model model){
        PageHelper.startPage(pagenum,5);
        List<Type> list=typeService.getAllTypes();
        PageInfo<Type> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }
    @GetMapping("/types/input")//跳到添加页面
    public String addType(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/input")//跳到修改页面
    public String updateType(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
    @PostMapping("/types/add")//添加操作
    public String toAddType(Type type, RedirectAttributes attributes){
        Type tp=typeService.findTypeByName(type.getName());
        if(tp!=null){
            attributes.addFlashAttribute("msg","不能添加重复的分类");
            return "redirect:/admin/types/input";
        }else{
            attributes.addFlashAttribute("msg","添加成功");
        }
        typeService.save(type);
        return "redirect:/admin/types";
    }
    @PostMapping("/types/update")//修改操作
    public String toUpdateType(Type type,RedirectAttributes attributes){
        Type tp=typeService.findTypeByName(type.getName());
        if(tp!=null){
            attributes.addFlashAttribute("msg","不能添加重复的分类");
            return "redirect:/admin/types/input";
        }else{
            attributes.addFlashAttribute("msg","修改成功");
        }
        typeService.update(type);
        return "redirect:/admin/types";
    }
    @GetMapping("types/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg","删除成功");
        return "redirect:/admin/types";
    }
}
