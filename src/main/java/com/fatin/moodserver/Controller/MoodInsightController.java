package com.fatin.moodserver.Controller;


import com.fatin.moodserver.Model.Respose.InsightResponse.MoodSummaryResponse;
import com.fatin.moodserver.Service.MoodInsightService;
import com.fatin.moodserver.Service.UserInfoService;
import com.fatin.moodserver.Service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insight")
public class MoodInsightController {


    private final UserInfoService userInfoService;
    private final UserService userService;
    private final MoodInsightService moodInsightService;


    public MoodInsightController(UserInfoService userInfoService, UserService userService, MoodInsightService moodInsightService){
        this.userInfoService = userInfoService;
        this.userService = userService;
        this.moodInsightService = moodInsightService;
    }
    @GetMapping("/summary")
    public MoodSummaryResponse getSummary(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestParam("timeframe") String timeframe) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        String email = userInfoService.getUserEmail(accessToken);
        MoodSummaryResponse moodSummaryResponse = moodInsightService.getSummaryInsight(userService.getUser(email), timeframe);
        return moodSummaryResponse;
    }




}
