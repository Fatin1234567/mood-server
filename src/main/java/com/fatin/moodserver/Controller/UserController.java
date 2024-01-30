package com.fatin.moodserver.Controller;

import com.fatin.moodserver.Service.UserInfoService;
import com.fatin.moodserver.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserInfoService userInfoService;
    private final UserService userService;

    public UserController(UserInfoService userInfoService, UserService userService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
    }

    @GetMapping("/name")
    public String getUserName(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        // Extract the token from the Authorization header
        String accessToken = authorizationHeader.replace("Bearer ", "");
/*
        String email = userInfoService.getUserEmail(accessToken);
*/
        return userInfoService.getUserName(accessToken);
    }

    @GetMapping("/test")
    public String getUserTest(@RequestParam String accessToken) {
        return accessToken;
    }
}