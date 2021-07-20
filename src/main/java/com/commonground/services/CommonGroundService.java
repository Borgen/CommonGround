package com.commonground.services;

import com.commonground.entity.CommonGround;
import com.commonground.entity.User;
import com.commonground.repositories.CommonGroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommonGroundService {

    @Autowired
    private CommonGroundRepository commonGroundRepository;

    public void save(CommonGround commonGround){
        commonGroundRepository.save(commonGround);
    }

    public List<CommonGround> getByUser(User user){
        return commonGroundRepository.findByUser(user);
    }
}
