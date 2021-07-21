package com.commonground.authentication;

import com.commonground.dto.GoogleUserInfo;
import com.commonground.entity.User;
import com.commonground.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Autowired
    private UserService userService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getUser() throws Exception {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       GoogleUserInfo googleUserInfo = new GoogleUserInfo(principal.getAttributes());
       Optional<User> optionalUser = userService.findByEmail(googleUserInfo.getEmail());
       if(optionalUser.isPresent()){
           return optionalUser.get();
       }
       throw new Exception("User not found!");
    }

}
