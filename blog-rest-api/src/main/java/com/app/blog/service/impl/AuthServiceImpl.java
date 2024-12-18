package com.app.blog.service.impl;

import com.app.blog.dto.LoginDTO;
import com.app.blog.dto.RegisterDTO;
import com.app.blog.entity.Role;
import com.app.blog.entity.User;
import com.app.blog.exception.BlogAPIException;
import com.app.blog.exception.RoleNotFoundException;
import com.app.blog.repository.RoleRepository;
import com.app.blog.repository.UserRepository;
import com.app.blog.security.JwtTokenProvider;
import com.app.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        //add check if username exists in database
        if(Boolean.TRUE.equals(userRepository.existsByUsername(registerDTO.getUsername()))){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }
        //add check if email exists in database
        if(Boolean.TRUE.equals(userRepository.existsByEmail(registerDTO.getEmail()))){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }
        User user = new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").
                orElseThrow(()-> new RoleNotFoundException(HttpStatus.BAD_REQUEST, "No role found for User"));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
        return "User registered successfully";
    }
}
