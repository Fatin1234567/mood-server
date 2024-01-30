package com.fatin.moodserver.Model;

public class UserRegistrationResponse {
    private final boolean success;
    private final String email;
    private final String username;

    public UserRegistrationResponse(boolean success, String email, String username) {
        this.success = success;
        this.email = email;
        this.username = username;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}