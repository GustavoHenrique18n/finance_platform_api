package com.finance.api.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.finance.api.LoggedUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/login") || request.getServletPath().equals("/registrar")){
            filterChain.doFilter(request, response);
        }else {
            String authorization = request.getHeader(AUTHORIZATION);
            if(authorization != null && authorization.startsWith("Bearer ")){
                try {

                    String token = authorization.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("authtoken".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String email = decodedJWT.getSubject();
                    LoggedUser.setUserLoggedInId(decodedJWT.getIssuer());

                    List<GrantedAuthority> role_user = AuthorityUtils.createAuthorityList("ROLE_USER");
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(email , null , role_user);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);

                }catch (Exception e){
                    System.out.println(e.getMessage());
                    response.setStatus(403);
                    response.setHeader("error" , e.getMessage());
                }
            }else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
