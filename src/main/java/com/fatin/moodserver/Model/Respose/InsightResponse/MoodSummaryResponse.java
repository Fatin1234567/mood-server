package com.fatin.moodserver.Model.Respose.InsightResponse;
import java.util.List;
import java.util.Map;

public class MoodSummaryResponse {
    private double averageIntensity;

    public double getAverageIntensity() {
        return averageIntensity;
    }

    public void setAverageIntensity(double averageIntensity) {
        this.averageIntensity = averageIntensity;
    }

    public String getMostCommonMood() {
        return mostCommonMood;
    }

    public void setMostCommonMood(String mostCommonMood) {
        this.mostCommonMood = mostCommonMood;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }

    public Map<String, Integer> getMoodCounts() {
        return moodCounts;
    }

    public void setMoodCounts(Map<String, Integer> moodCounts) {
        this.moodCounts = moodCounts;
    }

    private String mostCommonMood;
    private int entryCount;
    private Map<String, Integer> moodCounts; // Mood and their counts
    private List<MoodDetail> dailyMoodDetails; // Detailed entries for each day

    // Constructor
    public MoodSummaryResponse(double averageIntensity, String mostCommonMood, int entryCount, Map<String, Integer> moodCounts, List<MoodDetail> dailyMoodDetails) {
        this.averageIntensity = averageIntensity;
        this.mostCommonMood = mostCommonMood;
        this.entryCount = entryCount;
        this.moodCounts = moodCounts;
        this.dailyMoodDetails = dailyMoodDetails;
    }

    // Getters and Setters
    // Include getters and setters for new and existing fields

    public List<MoodDetail> getDailyMoodDetails() {
        return dailyMoodDetails;
    }

    public void setDailyMoodDetails(List<MoodDetail> dailyMoodDetails) {
        this.dailyMoodDetails = dailyMoodDetails;
    }

    // Existing getters and setters...
}


