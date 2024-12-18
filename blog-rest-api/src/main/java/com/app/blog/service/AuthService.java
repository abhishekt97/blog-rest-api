package com.app.blog.service;

import com.app.blog.dto.LoginDTO;
import com.app.blog.dto.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO);
}
