package com.finance.api.service;

import com.finance.api.entity.Users;
import com.finance.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void register(Users user) {
        Optional<Users> exists = usersRepository.findUserByEmail(user.getEmail());
        if(exists.isPresent()){
            throw new IllegalStateException("email already exists");
        }
        usersRepository.save(user);
    }

    public String auth(Users user) {
        Optional<Users> exists = usersRepository.findUserByEmail(user.getEmail());
        if(exists.isPresent()){
            Users alreadyRegisterUser = exists.get();
            if(user.getPassword().equals(alreadyRegisterUser.getPassword())){
                return "logado";
            }else {
                return "senha errada";
            }
        }else {
            return "usuario inexistente";
        }
    }

}
