package com.fatin.moodserver.Model;

public class MoodEntryAnomaly {

    private int intensity;
    private String reason;
    private String timestamp; // or LocalDateTime if you prefer to work with date-time objects
    private String mood;

    // Constructors

    public MoodEntryAnomaly(int intensity, String reason, String timestamp, String mood) {
        this.intensity = intensity;
        this.reason = reason;
        this.timestamp = timestamp;
        this.mood = mood;
    }

    public MoodEntryAnomaly(){}



    // Getters and Setters


    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }



    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

}
