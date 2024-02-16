package com.fatin.moodserver.Service;

import com.fatin.moodserver.Model.MoodEntry;
import com.fatin.moodserver.Model.Respose.InsightResponse.MoodSummaryResponse;
import com.fatin.moodserver.Model.UserAccount;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

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
            // For the month case, go back to the first day of the previous month
            startDateTime = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        } else {
            // Assume numerical input represents weeks
            try {
                int weeks = Integer.parseInt(timeFrame);
                if (weeks > 0 && weeks <= 4) { // Assuming 4 as the maximum for weekly intervals
                    startDateTime = now.minusWeeks(weeks).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
                } else {
                    throw new IllegalArgumentException("Weeks must be between 1 and 4");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Unsupported time frame: " + timeFrame);
            }
        }

        return startDateTime;
    }

    private LocalDateTime calculateEndDate(String timeFrame) {
        LocalDate now = LocalDate.now();
        LocalDateTime endDateTime;

        if ("month".equalsIgnoreCase(timeFrame)) {
            // End of the previous month
            endDateTime = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);
        } else {
            // Assuming the end date is always the current time for weekly intervals
            // This could be adjusted if the end date needs to be the last day of the last week
            try {
                int weeks = Integer.parseInt(timeFrame);
                if (weeks > 0 && weeks <= 4) {
                    // For simplicity, end date is now; adjust based on requirements
                    endDateTime = now.atTime(23, 59, 59);
                } else {
                    throw new IllegalArgumentException("Weeks must be between 1 and 4");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Unsupported time frame: " + timeFrame);
            }
        }

        return endDateTime;
    }


    private MoodSummaryResponse processMoodEntries(List<MoodEntry> moodEntries) {
        // Calculate insights such as average mood, most common mood, etc.
        // ...

        return null;
    }
}