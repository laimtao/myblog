package com.lmt.service;

import com.lmt.domain.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface UserService {
    User queryUser(String username,String password);

    void saveUser(User user);


    User findUser(String username);

    void changePassword(Long id,String password);

}
