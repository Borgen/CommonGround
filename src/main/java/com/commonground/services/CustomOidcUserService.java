package com.commonground.services;

import com.commonground.dto.*;
import com.commonground.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.oauth2.client.oidc.userinfo.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.*;
import org.springframework.security.oauth2.core.oidc.user.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        try {
            return processOidcUser(userRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }


    private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {

        //TODO: provide other oauth services

        GoogleUser googleUser = new GoogleUser(oidcUser.getAuthorities(), oidcUser.getIdToken(), oidcUser.getUserInfo());

        // see what other data from userRequest or oidcUser you need

        Optional<User> userOptional = userService.findByEmail(googleUser.getEmail());
        if (!userOptional.isPresent()) {
            User user = new User(googleUser.getFirstName(), googleUser.getLastName(), googleUser.getEmail());
            user.setoAuthId(googleUser.getId());
            userService.save(user);
        }

        return googleUser;
    }
}