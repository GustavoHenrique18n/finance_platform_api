package com.finance.api.controller;

import com.finance.api.entity.Users;
import com.finance.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "registrar")
    public void RegisterNewUser (@RequestBody Users user) {
        userService.register(user);
    }

    @PostMapping(path = "login")
    public void authUser (@RequestBody Users user) {
        userService.auth(user);
    }
}
