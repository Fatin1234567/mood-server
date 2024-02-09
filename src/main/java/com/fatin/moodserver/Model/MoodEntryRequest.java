package com.fatin.moodserver.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class MoodEntryRequest {
    @JsonProperty("userMood")
    private String mood;

    @JsonProperty("userIntensity")
    private Integer intensity;

    @JsonProperty("moodDetails")
    private String reason;
    private LocalDateTime timestamp; // Ensure the client sends this in ISO-8601 format

    // Getters and setters

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}