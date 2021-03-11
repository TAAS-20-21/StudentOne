package com.gruppo13.AuthenticationMS.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import com.gruppo13.AuthenticationMS.validator.PasswordMatches;
import lombok.Data;

@Data
@PasswordMatches
public class SignUpRequest {

    private Long id;

    private String providerUserId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotEmpty
    private String email;

    private SocialProvider socialProvider;

    @Size(min = 6, message = "{Size.userDto.password}")
    private String password;

    @NotEmpty
    private String matchingPassword;

    public SignUpRequest(String providerUserId, String name, String surname, String email, String password, SocialProvider socialProvider) {
        this.providerUserId = providerUserId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.socialProvider = socialProvider;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String providerUserID;
        private String name;
        private String surname;
        private String email;
        private String password;
        private SocialProvider socialProvider;

        public Builder addProviderUserID(final String userID) {
            this.providerUserID = userID;
            return this;
        }

        public Builder addName(final String name) {
            this.name = name;
            return this;
        }

        public Builder addSurname(final String surname) {
            this.surname = surname;
            return this;
        }

        public Builder addEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder addPassword(final String password) {
            this.password = password;
            return this;
        }

        public Builder addSocialProvider(final SocialProvider socialProvider) {
            this.socialProvider = socialProvider;
            return this;
        }

        public SignUpRequest build() {
            return new SignUpRequest(providerUserID, name, surname, email, password, socialProvider);
        }
    }
}