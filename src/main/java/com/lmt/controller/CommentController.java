package com.lmt.controller;
import com.lmt.domain.Comment;
import com.lmt.domain.User;
import com.lmt.service.BlogService;
import com.lmt.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    /*@Value("${comment.avatar}")
    private String avatar;*/
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.getCommentByBlogId(blogId));
        return "blog :: commentList";
    }
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getDetailBlog(blogId));
        comment.setBlogId(blogId);
        User user= (User) session.getAttribute("user");
        if (user.getType()==1){
            comment.setAdminComment(true);
        }
        comment.setAvatar(user.getAvatar());
        comment.setNickname(user.getNickname());
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
