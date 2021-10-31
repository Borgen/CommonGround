package com.commonground.dto;

import org.springframework.security.core.*;
import org.springframework.security.oauth2.core.oidc.*;
import org.springframework.security.oauth2.core.oidc.user.*;

import java.util.*;

public class GoogleUser extends DefaultOidcUser {


    public GoogleUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken) {
        super(authorities, idToken);
    }

    public GoogleUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, String nameAttributeKey) {
        super(authorities, idToken, nameAttributeKey);
    }

    public GoogleUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, OidcUserInfo userInfo) {
        super(authorities, idToken, userInfo);
    }

    public GoogleUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, OidcUserInfo userInfo, String nameAttributeKey) {
        super(authorities, idToken, userInfo, nameAttributeKey);
    }

    public String getId() {
        return (String) getAttributes().get("sub");
    }

    public String getFirstName() {
        return (String) getAttributes().get("given_name");
    }

    public String getLastName() {
        return (String) getAttributes().get("family_name");
    }

    public String getEmail() {
        return (String) getAttributes().get("email");
    }

    public String getPictureURL(){
        return (String) getAttributes().get("picture");
    }

    public String getName(){
        return (String) getAttributes().get("name");
    }

}
