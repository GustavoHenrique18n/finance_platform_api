package com.finance.api.controller;

import com.finance.api.entity.Users;
import com.finance.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @PostMapping(path = "registrar")
    @ResponseBody
    public String  RegisterNewUser (@RequestBody Users user) {
        return userService.register(user);
    }

}
