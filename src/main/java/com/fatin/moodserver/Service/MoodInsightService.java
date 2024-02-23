package com.fatin.moodserver.Service;

import com.fatin.moodserver.Model.MoodEntry;
import com.fatin.moodserver.Model.Respose.InsightResponse.MoodDetail;
import com.fatin.moodserver.Model.Respose.InsightResponse.MoodSummaryResponse;
import com.fatin.moodserver.Model.UserAccount;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class MoodInsightService {

    private final MoodEntryService moodEntryService;

    public MoodInsightService(MoodEntryService moodEntryService){
        this.moodEntryService = moodEntryService;
    }

    public MoodSummaryResponse getSummaryInsight(UserAccount userAccount, String timeFrame) {
        LocalDateTime startDateTime = calculateStartDate(timeFrame);
        LocalDateTime endDateTime = calculateEndDate(timeFrame, startDateTime);

        // Fetch mood data from the database using LocalDateTime for start and end
        List<MoodEntry> moodEntries = moodEntryService.getMoodEntriesBetween(userAccount, startDateTime, endDateTime);

        // Process the entries to calculate insights
        MoodSummaryResponse summary = processMoodEntries(moodEntries, startDateTime);

        // Return the summarized insights
        return summary;
    }


    private LocalDateTime calculateStartDate(String timeFrame) {
        LocalDate now = LocalDate.now();
        LocalDateTime startDateTime;

        if (timeFrame.toLowerCase().startsWith("month")) {
            startDateTime = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        } else {
            int weeks = Integer.parseInt(timeFrame.substring(4)); // Extract the number after 'week'
            startDateTime = now.minusWeeks(weeks).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        }

        return startDateTime;
    }

    private LocalDateTime calculateEndDate(String timeFrame, LocalDateTime startDate) {
        LocalDateTime endDateTime;

        if (timeFrame.toLowerCase().startsWith("month")) {
            // If "month1" is the timeframe, go to the last day of the start date's month.
            endDateTime = startDate.with(TemporalAdjusters.firstDayOfNextMonth())
                    .minusDays(1)
                    .withHour(23)
                    .withMinute(59)
                    .withSecond(59);
        } else {
            // Extract the number after 'week'
            int weeks = Integer.parseInt(timeFrame.substring(4));
            // Find the end of the week's Sunday for the given start date's week.
            // The start date is assumed to be a Monday based on previous context provided.
            endDateTime = startDate.plusWeeks(weeks - 1) // Subtract 1 because startDate is "week1"
                    .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                    .withHour(23)
                    .withMinute(59)
                    .withSecond(59);
        }

        return endDateTime;
    }




    public MoodSummaryResponse processMoodEntries(List<MoodEntry> moodEntries, LocalDateTime startDate) {
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

        LocalDate startLocalDate = startDate.toLocalDate(); // Convert startDate to LocalDate

        List<MoodDetail> dailyMoodDetails = moodEntries.stream()
                .map(entry -> {
                    // Calculate the week number for each entry relative to startDate
                    long week = ChronoUnit.WEEKS.between(startLocalDate, entry.getTimestamp().toLocalDate()) + 1;

                    return new MoodDetail(entry.getTimestamp().toLocalDate(), entry.getMood(), entry.getIntensity(), entry.getReason(), (int) week);
                })
                .collect(Collectors.toList());

        return new MoodSummaryResponse(averageIntensity, mostCommonMood, moodEntries.size(), moodCounts, dailyMoodDetails);
    }

}