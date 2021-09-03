package com.finance.api.service;

import com.finance.api.entity.Users;
import com.finance.api.exception.ApiRequestException;
import com.finance.api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(Users user) {
        Optional<Users> exists = usersRepository.findUserByEmail(user.getEmail());
        if(exists.isPresent()){
            throw new ApiRequestException("id nao encontrado");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Users> userExists =
                Optional.ofNullable(usersRepository.findUserByEmail(email))
                .orElseThrow(()-> new UsernameNotFoundException(" email nao encontrado "));

                Users user = userExists.get();
                List<GrantedAuthority> role_user = AuthorityUtils.createAuthorityList("ROLE_USER");
                return new User(user.getEmail() , user.getPassword() , role_user);
    }

}
