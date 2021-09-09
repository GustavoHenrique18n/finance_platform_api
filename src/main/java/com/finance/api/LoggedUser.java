package com.finance.api;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;


public class LoggedUser   {
    @Getter
    @Setter
    public static User userLogged;
}
