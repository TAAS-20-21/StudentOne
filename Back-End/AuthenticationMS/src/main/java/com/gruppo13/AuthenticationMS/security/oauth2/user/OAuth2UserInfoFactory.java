package com.gruppo13.AuthenticationMS.security.oauth2.user;

import com.gruppo13.AuthenticationMS.dto.SocialProvider;
import com.gruppo13.AuthenticationMS.exception.OAuth2AuthProcessingExc;

import java.util.Map;


public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(SocialProvider.GOOGLE.getProviderType())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthProcessingExc("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}