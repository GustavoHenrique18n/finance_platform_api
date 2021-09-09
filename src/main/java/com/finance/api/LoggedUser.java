package com.finance.api;


import lombok.Getter;
import lombok.Setter;

public class LoggedUser   {
    @Getter
    @Setter
    public static String userLoggedInId;

    public static Long convertStringtoLong (String JWTIssuer) {
        return Long.valueOf(JWTIssuer);
    }
}