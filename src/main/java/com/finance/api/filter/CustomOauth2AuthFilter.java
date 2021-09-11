package com.finance.api.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.api.entity.Users;
import com.finance.api.repository.UsersRepository;
import com.finance.api.security.oauth.CustomOauth2User;
import com.finance.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class CustomOauth2AuthFilter extends SimpleUrlAuthenticationSuccessHandler {

    private final UsersRepository usersRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOauth2User oauth2User = (CustomOauth2User)authentication.getPrincipal();
        String email = oauth2User.getEmail();
        String name = oauth2User.getName();
        Optional<Users> googleUser = usersRepository.findUserByEmail(email);

        if(googleUser.isPresent()){
            userService.updateUserLogged(email);
            EmitNewJWTToken(email , response);
        }else {
            userService.saveAUserLoggedWithGoogle(email , name);
            EmitNewJWTToken(email , response);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    public void EmitNewJWTToken(String Email , HttpServletResponse response) throws IOException {
        Algorithm algorithm = Algorithm.HMAC256("authtoken".getBytes());
        String userId = Long.toString(usersRepository.findIdByEmail(Email));

        String access_token =
                JWT.create()
                        .withSubject(Email)
                        .withIssuer(userId)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .sign(algorithm);
        String refresh_token =
                JWT.create()
                        .withSubject(Email)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                        .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token" , access_token);
        tokens.put("refresh_token" , refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream() , tokens);
    }

}
