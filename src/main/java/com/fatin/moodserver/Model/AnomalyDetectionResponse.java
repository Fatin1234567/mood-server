package com.fatin.moodserver.Model;

import java.util.List;

public class AnomalyDetectionResponse {
    private double meanIntensity;
    private double stdDevIntensity;
    private List<MoodEntryAnomaly> anomalies;

    public AnomalyDetectionResponse() {
    }

    public AnomalyDetectionResponse(double meanIntensity, double stdDevIntensity, List<MoodEntryAnomaly> anomalies) {
        this.meanIntensity = meanIntensity;
        this.stdDevIntensity = stdDevIntensity;
        this.anomalies = anomalies;
    }

    // Getters and setters
    public double getMeanIntensity() {
        return meanIntensity;
    }

    public void setMeanIntensity(double meanIntensity) {
        this.meanIntensity = meanIntensity;
    }

    public double getStdDevIntensity() {
        return stdDevIntensity;
    }

    public void setStdDevIntensity(double stdDevIntensity) {
        this.stdDevIntensity = stdDevIntensity;
    }

    public List<MoodEntryAnomaly> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<MoodEntryAnomaly> anomalies) {
        this.anomalies = anomalies;
    }

    // Nested class to represent an individual anomaly
    public static class MoodEntryAnomaly {
        private int intensity;
        private String reason;
        private String timestamp; // or LocalDateTime if you prefer to work with date-time objects
        private String mood;
        private boolean anomaly;

        // Constructors, getters, and setters

        public MoodEntryAnomaly() {
        }

        public MoodEntryAnomaly(int intensity, String reason, String timestamp, String mood, boolean anomaly) {
            this.intensity = intensity;
            this.reason = reason;
            this.timestamp = timestamp;
            this.mood = mood;
            this.anomaly = anomaly;
        }

        // Getters and setters
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

        public boolean isAnomaly() {
            return anomaly;
        }

        public void setAnomaly(boolean anomaly) {
            this.anomaly = anomaly;
        }
    }
}
