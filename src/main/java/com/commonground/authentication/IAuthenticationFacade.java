package com.commonground.authentication;

import com.commonground.entity.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface IAuthenticationFacade {
    public Authentication getAuthentication();
    public Optional<User> getUser();

}
