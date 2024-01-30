package com.fatin.moodserver.Controller;

import com.fatin.moodserver.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/name")
    public String getUserName(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        // Extract the token from the Authorization header
        String accessToken = authorizationHeader.replace("Bearer ", "");
        String email = userInfoService.getUserEmail(accessToken);
        return userInfoService.getUserName(accessToken);
    }

    @GetMapping("/test")
    public String getUserTest(@RequestParam String accessToken) {
        return accessToken;
    }
}