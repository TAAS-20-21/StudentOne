package com.gruppo13.AuthenticationMS.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.gruppo13.AuthenticationMS.dto.LocalUser;
import com.gruppo13.AuthenticationMS.dto.SocialProvider;
import com.gruppo13.AuthenticationMS.dto.UserInfo;
import com.gruppo13.AuthenticationMS.model.Role;
import com.gruppo13.AuthenticationMS.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class GeneralUtils {

    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getTypeRole().toString()));
        }
        return authorities;
    }

    public static SocialProvider toSocialProvider(String providerId) {
        for (SocialProvider socialProvider : SocialProvider.values()) {
            if (socialProvider.getProviderType().equals(providerId)) {
                return socialProvider;
            }
        }
        return SocialProvider.LOCAL;
    }

    public static UserInfo buildUserInfo(LocalUser localUser) {
        List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        User user = localUser.getUser();
        return new UserInfo(user.getId().toString(), user.getName(), user.getSurname(), user.getEmail(), roles);
    }
}