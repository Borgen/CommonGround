package com.commonground.services;


import com.commonground.entity.*;
import com.commonground.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User newUser){
        userRepository.save(newUser);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> list(){
        return userRepository.findAll();
    }

}
