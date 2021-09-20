package com.finance.api.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.api.entity.Users;
import com.finance.api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class RegisterEmitJwt {
    private final UsersRepository usersRepository;
    HttpServletResponse response;
    public String emit(Users users){
        Algorithm algorithm = Algorithm.HMAC256("authtoken".getBytes());
        String userId = Long.toString(usersRepository.findIdByEmail(users.getEmail()));

        String access_token =
                JWT.create()
                        .withSubject(users.getEmail())
                        .withIssuer(userId)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .sign(algorithm);
        String refresh_token =
                JWT.create()
                        .withSubject(users.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                        .sign(algorithm);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token" , access_token);
        jsonObject.put("refresh_token" , refresh_token);
        return  jsonObject.toString();
    }
}
