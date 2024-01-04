package com.fatin.moodserver.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Service
public class UserInfoService {

    private final RestTemplate restTemplate;

    @Autowired
    public UserInfoService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getUserInfo(String accessToken) {
        String url = "https://api.amazon.com/user/profile";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            // Handle exceptions appropriately
            return "Error: " + e.getMessage();
        }
    }

    public String getUserName(String accessToken) {
        String userInfoJson = getUserInfo(accessToken);
        try {
            JSONObject jsonObj = new JSONObject(userInfoJson);
            return jsonObj.optString("name", "Default Name"); // "Default Name" is a fallback if the name is not present
        } catch (Exception e) {
            // Handle exceptions appropriately
            return "Error parsing user info: " + e.getMessage();
        }
    }
}
