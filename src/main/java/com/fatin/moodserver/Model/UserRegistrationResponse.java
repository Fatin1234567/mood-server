package com.fatin.moodserver.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegistrationResponse {


    @JsonProperty("isRegistered")
    private final boolean isRegistered;

    @JsonProperty("email")
    private final String email;
    @JsonProperty("username")

    private final String username;

    public UserRegistrationResponse(boolean isRegistered, String email, String username) {
        this.isRegistered = isRegistered;
        this.email = email;
        this.username = username;
    }

    // Getters
    public boolean isRegistered() {
        return isRegistered;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}