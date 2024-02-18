package com.fatin.moodserver.Service;

import com.fatin.moodserver.Model.MoodEntry;
import com.fatin.moodserver.Model.Respose.InsightResponse.MoodSummaryResponse;
import com.fatin.moodserver.Model.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoodInsightServiceTest {

    @Mock
    private MoodEntryService moodEntryService;

    @InjectMocks
    private MoodInsightService moodInsightService;

    private UserAccount user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserAccount("1L", "Test User");
    }

    @Test
    public void testProcessMoodEntriesWithNoEntries() {
        List<MoodEntry> moodEntries = List.of();
        MoodSummaryResponse response = moodInsightService.processMoodEntries(moodEntries);

        assertThat(response.getAverageIntensity()).isEqualTo(0);
        assertThat(response.getMostCommonMood()).isEmpty();
        assertThat(response.getEntryCount()).isZero();
        assertThat(response.getMoodCounts()).isEmpty();
        assertThat(response.getDailyMoodDetails()).isEmpty();
    }

    @Test
    public void testProcessMoodEntriesWithMultipleEntries() {
        UserAccount user = new UserAccount("1L", "Test User");

        List<MoodEntry> moodEntries = Arrays.asList(
                new MoodEntry(user, "Happy", 8, LocalDateTime.now(),"LocalDateTime.now()" ),
                new MoodEntry(user, "Sad", 4, LocalDateTime.now().minusDays(1),"Bad day"),
                new MoodEntry(user, "Happy", 6, LocalDateTime.now().minusDays(2), "Another good day")
        );

        MoodSummaryResponse response = moodInsightService.processMoodEntries(moodEntries);

        assertThat(response.getAverageIntensity()).isEqualTo((8 + 4 + 6) / 3.0);
        assertThat(response.getMostCommonMood()).isEqualTo("Happy");
        assertThat(response.getEntryCount()).isEqualTo(3);

        Map<String, Integer> expectedMoodCounts = new HashMap<>();
        expectedMoodCounts.put("Happy", 2);
        expectedMoodCounts.put("Sad", 1);
        assertThat(response.getMoodCounts()).containsAllEntriesOf(expectedMoodCounts);

        assertThat(response.getDailyMoodDetails()).hasSize(3);
    }
    @Test
    void testGetSummaryInsightForLastWeek() {
        // Create mock entries for two weeks
        List<MoodEntry> mockEntries = createMockEntriesForTwoWeeks(user);

        // Mock the moodEntryService to return these entries when queried
        when(moodEntryService.getMoodEntriesBetween(eq(user), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenAnswer(invocation -> {
                    LocalDateTime start = invocation.getArgument(1, LocalDateTime.class);
                    LocalDateTime end = invocation.getArgument(2, LocalDateTime.class);
                    return mockEntries.stream()
                            .filter(entry -> !entry.getTimestamp().isBefore(start) && !entry.getTimestamp().isAfter(end))
                            .collect(Collectors.toList());
                });

        // Adjust the moodInsightService method to filter entries based on the timeframe
        MoodSummaryResponse summary = moodInsightService.getSummaryInsight(user, "1");

        // Assertions
        assertThat(summary.getEntryCount()).isEqualTo(2); // Expecting 2 entries for the last week
        // Verify other aspects of the summary as needed, such as average intensity or mood counts
    }

    private List<MoodEntry> createMockEntriesForTwoWeeks(UserAccount user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfLastWeek = now.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfLastWeek = startOfLastWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return List.of(
                new MoodEntry(user, "Happy", 8, startOfLastWeek.plusDays(1), "Had a great day"), // Last week
                new MoodEntry(user, "Sad", 6, startOfLastWeek.plusDays(2), "Had a bad day"), // Last week
                new MoodEntry(user, "Worried", 5, endOfLastWeek.plusDays(1), "Work stress"), // This week
                new MoodEntry(user, "Content", 7, endOfLastWeek.plusDays(2), "All is well") // This week
        );
    }
}
