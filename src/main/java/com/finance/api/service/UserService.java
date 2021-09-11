package com.finance.api.service;

import com.finance.api.entity.Users;
import com.finance.api.exception.authRequestException;
import com.finance.api.regex.RegexUserEmail;
import com.finance.api.regex.RegexUserPassword;
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

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(Users user) {
        Optional<Users> exists = usersRepository.findUserByEmail(user.getEmail());
        if(exists.isPresent()){
            throw new authRequestException("usuario ja registrado");
        }

        Boolean emailVerificated = RegexUserEmail.userEmail(user.getEmail());
        Boolean passwordVerificated = RegexUserPassword.userPassword(user.getPassword());

        if(!passwordVerificated || !emailVerificated){
            throw new authRequestException("As credencias inseridas não seguem o esperado verifique os campos");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> userExists = usersRepository.findUserByEmail(email);

        if(userExists.isPresent()){
            Users user = userExists.get();
            List<GrantedAuthority> role_user = AuthorityUtils.createAuthorityList("ROLE_USER");
            return new User(user.getEmail() , user.getPassword() , role_user);
        }

        throw new UsernameNotFoundException(" credencial invalida ");

    }


    public void updateUserLogged(String email) {
        Optional<Users> userFound = usersRepository.findUserByEmail(email);
        if(userFound.isPresent()){
            Users user = userFound.get();
            user.setEmail(email);
            usersRepository.save(user);
        }
    }

    public void saveAUserLoggedWithGoogle(String email ,String name) {
        Users user = new Users();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode("loggedin"));
        usersRepository.save(user);
    }
}
