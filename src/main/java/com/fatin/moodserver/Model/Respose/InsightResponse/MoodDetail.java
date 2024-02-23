package com.fatin.moodserver.Model.Respose.InsightResponse;

import java.time.LocalDate;

public class MoodDetail {
    private LocalDate date;
    private String mood;
    private int intensity;
    private String reason;

    private int weekLabel;

    // Constructor
    public MoodDetail(LocalDate date, String mood, int intensity, String reason, int weekLabel) {
        this.date = date;
        this.mood = mood;
        this.intensity = intensity;
        this.reason = reason;
        this.weekLabel = weekLabel;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

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

}
