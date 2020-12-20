package com.gruppo13.AuthenticationMS.service;

import java.util.Map;
import java.util.Optional;

import com.gruppo13.AuthenticationMS.dto.LocalUser;
import com.gruppo13.AuthenticationMS.dto.SignUpRequest;
import com.gruppo13.AuthenticationMS.exception.UserAlreadyExistAuthenticationException;
import com.gruppo13.AuthenticationMS.model.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;


public interface UserService {

    public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}