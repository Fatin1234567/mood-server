package com.fatin.moodserver.Service;

import com.fatin.moodserver.Model.MoodEntry;
import com.fatin.moodserver.Model.Respose.InsightResponse.MoodDetail;
import com.fatin.moodserver.Model.Respose.InsightResponse.MoodSummaryResponse;
import com.fatin.moodserver.Model.UserAccount;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoodInsightService {

    private final MoodEntryService moodEntryService;

    public MoodInsightService(MoodEntryService moodEntryService){
        this.moodEntryService = moodEntryService;
    }

    public MoodSummaryResponse getSummaryInsight(UserAccount userAccount, String timeFrame) {
        LocalDateTime startDateTime = calculateStartDate(timeFrame);
        LocalDateTime endDateTime = calculateEndDate(timeFrame);

        // Fetch mood data from the database using LocalDateTime for start and end
        List<MoodEntry> moodEntries = moodEntryService.getMoodEntriesBetween(userAccount, startDateTime, endDateTime);

        // Process the entries to calculate insights
        MoodSummaryResponse summary = processMoodEntries(moodEntries);

        // Return the summarized insights
        return summary;
    }


    private LocalDateTime calculateStartDate(String timeFrame) {
        LocalDate now = LocalDate.now();
        LocalDateTime startDateTime;

        if ("month".equalsIgnoreCase(timeFrame)) {
            startDateTime = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        } else {
            int weeks = Integer.parseInt(timeFrame); // Assuming valid input for simplicity
            startDateTime = now.minusWeeks(weeks).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        }

        return startDateTime;
    }

    private LocalDateTime calculateEndDate(String timeFrame) {
        LocalDate now = LocalDate.now();
        LocalDateTime endDateTime;

        if ("month".equalsIgnoreCase(timeFrame)) {
            endDateTime = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);
        } else {
            int weeks = Integer.parseInt(timeFrame); // Assuming valid input for simplicity
            endDateTime = now.minusWeeks(weeks).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atTime(23, 59, 59);
        }

        return endDateTime;
    }




    public MoodSummaryResponse processMoodEntries(List<MoodEntry> moodEntries) {
        if (moodEntries.isEmpty()) {
            return new MoodSummaryResponse(0, "", 0, new HashMap<>(), List.of());
        }

        double averageIntensity = moodEntries.stream()
                .mapToInt(MoodEntry::getIntensity)
                .average()
                .orElse(0.0);

        Map<String, Long> moodFrequency = moodEntries.stream()
                .collect(Collectors.groupingBy(MoodEntry::getMood, Collectors.counting()));

        String mostCommonMood = moodFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

        Map<String, Integer> moodCounts = moodFrequency.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().intValue()));

        List<MoodDetail> dailyMoodDetails = moodEntries.stream()
                .map(entry -> new MoodDetail(entry.getTimestamp().toLocalDate(), entry.getMood(), entry.getIntensity(), entry.getReason()))
                .collect(Collectors.toList());

        return new MoodSummaryResponse(averageIntensity, mostCommonMood, moodEntries.size(), moodCounts, dailyMoodDetails);
    }
}