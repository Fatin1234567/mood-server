package com.fatin.moodserver.Service;

import com.fatin.moodserver.Model.UserInfoResponse;
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



    public UserInfoResponse getUserInfo(String accessToken) {
        String url = "https://api.amazon.com/user/profile";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            JSONObject jsonObj = new JSONObject(response.getBody());

            String name = jsonObj.optString("name", "Default Name");
            String email = jsonObj.optString("email", "No email provided");

            return new UserInfoResponse(name, email);
        } catch (Exception e) {
            // Handle exceptions appropriately
            // Depending on your error handling, you might want to throw an exception or return a response indicating the error
            return new UserInfoResponse("Error: " + e.getMessage(), "Error: " + e.getMessage());
        }
    }




    public String getUserName(String accessToken) {
        return getUserInfo(accessToken).getName();
    }

    public String getUserEmail(String accessToken) {
        return getUserInfo(accessToken).getEmail();
    }
}
