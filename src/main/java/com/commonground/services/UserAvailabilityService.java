package com.commonground.services;

import com.commonground.authentication.*;
import com.commonground.entity.UserAvailability;
import com.commonground.entity.User;
import com.commonground.repositories.UserAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAvailabilityService {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserAvailabilityRepository userAvailabilityRepository;

    public void save(UserAvailability userAvailability) throws Exception {
        userAvailability.setUser(authenticationFacade.getUser());
        userAvailabilityRepository.save(userAvailability);
    }

    public List<UserAvailability> getByUser() throws Exception {
        return userAvailabilityRepository.findByUser(authenticationFacade.getUser());
    }

    public UserAvailability getLatestAvailability() throws Exception {
        return userAvailabilityRepository.findTopByUserOrderByCreateDateDesc(authenticationFacade.getUser());
    }

}
