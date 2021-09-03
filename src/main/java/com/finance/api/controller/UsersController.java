package com.finance.api.controller;

import com.finance.api.entity.Users;
import com.finance.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @PostMapping(path = "registrar")
    public void RegisterNewUser (@RequestBody Users user) {
        userService.register(user);
    }

}
