package org.hotelbooking.controller;

import jakarta.security.auth.message.AuthException;
import org.hotelbooking.Reponse.ResponseHandler;
import org.hotelbooking.dto.UserDto;
import org.hotelbooking.models.User;
import org.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login( @RequestBody UserDto userDto) {
        try {
            User user = userService.loginUser(userDto);
            return ResponseHandler.getResponse(HttpStatus.OK, "Login successful", true, user);
        } catch (AuthException e) {
            HttpStatus status = e.getMessage().contains("not found") ?
                    HttpStatus.NOT_FOUND : HttpStatus.UNAUTHORIZED;
            return ResponseHandler.getResponse(status, e.getMessage(), false, null);
        }
    }
}