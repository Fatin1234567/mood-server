package com.fatin.moodserver.Model.Respose.InsightResponse;
import java.util.List;
import java.util.Map;

public class MoodSummaryResponse {
    private double averageIntensity;
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


