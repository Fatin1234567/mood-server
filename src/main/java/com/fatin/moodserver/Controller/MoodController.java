package com.fatin.moodserver.Controller;

import com.fatin.moodserver.Model.MoodEntryRequest;
import com.fatin.moodserver.Model.UserAccount;
import com.fatin.moodserver.Model.UserRegistrationResponse;
import com.fatin.moodserver.Service.MoodEntryService;
import com.fatin.moodserver.Service.UserInfoService;
import com.fatin.moodserver.Service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mood")
public class MoodController {

    private final UserInfoService userInfoService;
    private final UserService userService;
    private final MoodEntryService moodEntryService;

    public MoodController(UserInfoService userInfoService, UserService userService, MoodEntryService moodEntryService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
        this.moodEntryService = moodEntryService;
    }

    @GetMapping("/exists")
    public boolean userExists(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        String email = userInfoService.getUserEmail(accessToken);
        return userService.userExist(email);
    }

    @PostMapping("/add")
    public ResponseEntity<UserRegistrationResponse> addMood(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                            @RequestBody MoodEntryRequest moodEntryRequest) {
        String accessToken = authorizationHeader.replace("Bearer ", "");

        // Assuming you want to pass these details to the userService for processing
        String email = userInfoService.getUserEmail(accessToken);
        UserAccount user = userService.getUser(email);
        moodEntryService.saveMoodEntry(user, moodEntryRequest);
        return ResponseEntity.ok().build();
    }

}