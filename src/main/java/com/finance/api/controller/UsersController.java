package com.finance.api.controller;

import com.finance.api.entity.Users;
import com.finance.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


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
