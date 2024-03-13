package com.fatin.moodserver.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegistrationResponse {


    @JsonProperty("isRegistered")
    private final boolean isRegistered;

    @JsonProperty("email")
    private final String email;
    @JsonProperty("username")

    private final String username;

    @JsonProperty("firstName")

    private final String firstName;

    @JsonProperty("lastName")

    private final String lastName;

    public UserRegistrationResponse(boolean isRegistered, String email, String username) {
        this.isRegistered = isRegistered;
        this.email = email;
        this.username = username;
        this.firstName = getFirstName();
        this.lastName = getLastName();
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


    public String getFirstName() {
        if (username == null || !username.contains(" ")) {
            return ""; // or you could return null or throw an exception depending on your requirement
        }
        return username.substring(0, username.indexOf(" "));
    }

    public String getLastName() {
        if (username == null || !username.contains(" ")) {
            return ""; // or you could return null or throw an exception depending on your requirement
        }
        return username.substring(username.lastIndexOf(" ") + 1);
    }
}