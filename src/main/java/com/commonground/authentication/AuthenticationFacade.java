package com.commonground.authentication;

import com.commonground.dto.*;
import com.commonground.entity.User;
import com.commonground.services.UserService;
import org.slf4j.*;
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

    private Logger logger = LoggerFactory.getLogger(AuthenticationFacade.class);

    @Override
    public User getUser() throws Exception {
        GoogleUser googleUser = (GoogleUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userService.findByEmail(googleUser.getEmail());
        logger.info("User info get name: " + googleUser.getName() );
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new Exception("User not found!");
    }

}
