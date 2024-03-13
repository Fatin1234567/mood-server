package com.fatin.moodserver.Controller;

import com.fatin.moodserver.Model.UserInfoResponse;
import com.fatin.moodserver.Model.UserRegistrationResponse;
import com.fatin.moodserver.Service.EmailService;
import com.fatin.moodserver.Service.UserInfoService;
import com.fatin.moodserver.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserInfoService userInfoService;
    private final UserService userService;

    private final EmailService emailService;

    public UserController(UserInfoService userInfoService, UserService userService, EmailService emailService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
        this.emailService = emailService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        UserRegistrationResponse response = userService.registerUser(accessToken);
        if (!response.isRegistered()) emailService.sendWelcomeEmail(response.getEmail(), response.getUsername());
        return ResponseEntity.ok(response);

    }

    @GetMapping("/testEmail")
    public boolean testEmail() {
        return emailService.sendWelcomeEmail("sghsdjjdkd@gmail.com","Dilkush");
    }







    @GetMapping("/test")
    public String getUserTest(@RequestParam String accessToken) {
        return accessToken;
    }
}