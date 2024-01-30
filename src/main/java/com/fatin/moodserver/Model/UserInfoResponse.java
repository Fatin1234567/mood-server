package com.fatin.moodserver.Model;

public class UserInfoResponse {
    private String name;
    private String email;
    // Other fields, if necessary

    // Constructor
    public UserInfoResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // You can add a toString method for easier logging/debugging
}
