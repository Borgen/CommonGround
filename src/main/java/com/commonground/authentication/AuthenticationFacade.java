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
    public Optional<User> getUser() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       GoogleUserInfo googleUserInfo = new GoogleUserInfo(principal.getAttributes());
       Optional<User> user = userService.findByEmail(googleUserInfo.getEmail());
       return user;
    }

}
