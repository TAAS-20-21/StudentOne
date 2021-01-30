package com.gruppo13.AuthenticationMS.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.gruppo13.AuthenticationMS.config.CurrentUser;
import com.gruppo13.AuthenticationMS.dto.ApiResponse;
import com.gruppo13.AuthenticationMS.dto.LocalUser;
import com.gruppo13.AuthenticationMS.service.UserService;
import com.gruppo13.AuthenticationMS.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/authenticateToken")
    public ResponseEntity<?> authenticateToken(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }

    @GetMapping("/googleCredentials")
    public String getGoogleCredentials(@CurrentUser LocalUser user){
        return userService.getGoogleCredential(user.getUsername()).getAccessToken();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getContent() {
        return ResponseEntity.ok("Public content goes here");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserContent() {
        return ResponseEntity.ok("User content goes here");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok("Admin content goes here");
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getModeratorContent() {
        return ResponseEntity.ok("Moderator content goes here");
    }

}