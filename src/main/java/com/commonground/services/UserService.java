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

    public void add(User newUser){
        userRepository.save(newUser);
    }

    public List<User> list(){
        return userRepository.findAll();
    }

}
