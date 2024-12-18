package com.app.blog.controller;

import com.app.blog.dto.JwtAuthResponse;
import com.app.blog.dto.LoginDTO;
import com.app.blog.dto.RegisterDTO;
import com.app.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            tags = "LOGIN",
            description = "login to the application",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Success"
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error!"
                    )
            }
    )
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO loginDTO){

        String token = authService.login(loginDTO);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            tags = "REGISTER",
            description = "register to the application",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Success"
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error!"
                    )
            }
    )
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.CREATED);
    }
}
