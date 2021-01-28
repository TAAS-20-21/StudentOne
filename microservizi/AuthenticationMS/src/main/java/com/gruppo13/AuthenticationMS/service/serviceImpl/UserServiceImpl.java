package com.gruppo13.AuthenticationMS.service.serviceImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import com.gruppo13.AuthenticationMS.dto.LocalUser;
import com.gruppo13.AuthenticationMS.dto.SignUpRequest;
import com.gruppo13.AuthenticationMS.dto.SocialProvider;
import com.gruppo13.AuthenticationMS.exception.OAuth2AuthProcessingExc;
import com.gruppo13.AuthenticationMS.exception.UserAlreadyExistAuthenticationException;
import com.gruppo13.AuthenticationMS.model.Role;
import com.gruppo13.AuthenticationMS.model.TypeRole;
import com.gruppo13.AuthenticationMS.model.User;
import com.gruppo13.AuthenticationMS.repository.RoleRepository;
import com.gruppo13.AuthenticationMS.repository.UserRepository;
import com.gruppo13.AuthenticationMS.security.oauth2.user.OAuth2UserInfoFactory;
import com.gruppo13.AuthenticationMS.service.UserService;
import com.gruppo13.AuthenticationMS.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.gruppo13.AuthenticationMS.security.oauth2.user.OAuth2UserInfo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(value = "transactionManager")
    public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("User with email " + signUpRequest.getEmail() + " already exist");
        }
        User user = buildUser(signUpRequest);
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    private User buildUser(final SignUpRequest formDTO) {
        User user = new User();
        user.setName(formDTO.getName());
        user.setSurname(formDTO.getSurname());
        user.setEmail(formDTO.getEmail());
        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
        final HashSet<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByTypeRole(TypeRole.ROLE_USER).orElse(null));
        user.setRoles(roles);
        user.setProvider(formDTO.getSocialProvider().getProviderType());
        user.setProviderUserId(formDTO.getProviderUserId());
        return user;
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OAuth2AccessToken accessToken, OidcUserInfo userInfo) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
            throw new OAuth2AuthProcessingExc("Name not found from OAuth2 provider");
        }else if(StringUtils.isEmpty(oAuth2UserInfo.getSurname())){
            throw new OAuth2AuthProcessingExc("Surname not found from OAuth2 provider");
        } else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthProcessingExc("Email not found from OAuth2 provider");
        }
        SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
        User user = findUserByEmail(oAuth2UserInfo.getEmail());
        if (user != null) {
            if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
                throw new OAuth2AuthProcessingExc(
                        "Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(userDetails);
        }

        return LocalUser.create(user, attributes, idToken, userInfo);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setSurname(oAuth2UserInfo.getSurname());
        return userRepository.save(existingUser);
    }

    private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addName(oAuth2UserInfo.getName()).addSurname(oAuth2UserInfo.getSurname()).addEmail(oAuth2UserInfo.getEmail())
                .addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}