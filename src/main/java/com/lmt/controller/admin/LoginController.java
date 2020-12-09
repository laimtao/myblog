package com.lmt.controller.admin;

import com.lmt.domain.User;
import com.lmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class LoginController {
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    private UserService userService;
    @GetMapping("/admin/login")
    public String loginPage(){
        return "admin/login";
    }
    @GetMapping("/admin/index")
    public String toIndex(HttpSession session){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails= (UserDetails) principal;
        User user=userService.findUser(userDetails.getUsername());
        session.setAttribute("user",user);
        return "admin/index";
    }
    @GetMapping("/userLogin")
    public String userLogin() {
        return "userLogin";
    }

    @GetMapping("/register")
    public String userRegister(){
        return "register";
    }
    @PostMapping("/user/register")
    public String Register(User user,HttpSession session){
        user.setAvatar("/images/avatar.jpg");
        user.setCreateTime(new Date());
        user.setType(0);
        String code= (String) session.getAttribute("code");
        System.out.println(code);
        System.out.println(user.getMailVerifyCode());
        if (user.getMailVerifyCode().equals(code)){
            userService.saveUser(user);
            return "redirect:/userLogin";
        }else {
            return "redirect:/register";
        }
    }
    @PostMapping("/user/login")
    public String Login(User u, HttpSession session, RedirectAttributes attributes){
        User user=userService.queryUser(u.getUsername(),u.getPassword());
        String ccode= (String) session.getAttribute("CHECKCODE_SERVER");
        if (user!=null&&u.getCheckCode().equals(ccode)){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/";
        } else{
            attributes.addFlashAttribute("msg","用户名或密码或验证码错误");
            return "redirect:/userLogin";
        }
    }
    @GetMapping("/user/logout")
    public String userLogout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/userLogin";
    }
    @ResponseBody
    @GetMapping("/mailVerifyCode")
    public Boolean sendMail(HttpSession session,String email){
        String verificationCode = String.valueOf((int)((Math.random()*9+1)*100000));
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject("验证码");
        message.setText(verificationCode);
        message.setFrom("3228587445@qq.com");
        message.setTo(email);
        mailSender.send(message);
        session.setAttribute("code",verificationCode);
        return true;
    }
    @PostMapping("/user/changePassword")
    public String changePassword(HttpSession session,String password){
        User user= (User) session.getAttribute("user");
        userService.changePassword(user.getId(),password);
        return "redirect:/user/logout";
    }
    @GetMapping("/toChangePasswordPage")
    public String toChangePassword(){
        return "changePasswordPage";
    }
    /*@PostMapping("/admin/login")
    public String login(String username, String password, HttpSession session, RedirectAttributes attributes){
        User user=userService.queryUser(username, MD5Utils.code(password));
        if (user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("msg","用户名或密码错误");
            return "redirect:/admin";
        }
    }
    @GetMapping("admin/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }*/
}
