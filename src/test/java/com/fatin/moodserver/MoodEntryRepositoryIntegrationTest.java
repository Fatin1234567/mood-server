package com.fatin.moodserver;

import com.fatin.moodserver.Model.MoodEntry;
import com.fatin.moodserver.Model.UserAccount;
import com.fatin.moodserver.Repository.MoodEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class MoodEntryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MoodEntryRepository moodEntryRepository;

    private UserAccount user;
    private UserAccount user2;
    private MoodEntry moodEntry1;
    private MoodEntry moodEntry2;

    @BeforeEach
    void setUp() {
        // Set up UserAccount and MoodEntry instances here
        user = new UserAccount(); // Assume UserAccount has a no-args constructor
        user.setEmail("test@example.com"); // Set required fields
        user.setUserName("Test User");
        user2 = new UserAccount();
        user2.setEmail("test2example.com"); // Set required fields
        user2.setUserName("Test User2");
        entityManager.persist(user);
        entityManager.persist(user2);

        entityManager.flush();
    }

    @Test
    void whenFindByUserAndTimestampBetween_thenRetrieveSevenEntriesFromLastTwoWeeks() {
        List<MoodEntry> mockEntries = createMockEntries(user);
        mockEntries.forEach(entry -> entityManager.persist(entry));
        entityManager.flush();

        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);
        LocalDateTime now = LocalDateTime.now();

        List<MoodEntry> entriesFromLastTwoWeeks = moodEntryRepository.findByUserAndTimestampBetween(user, twoWeeksAgo, now);

        assertThat(entriesFromLastTwoWeeks).hasSize(7);
        assertThat(entriesFromLastTwoWeeks).allMatch(entry -> entry.getTimestamp().isAfter(twoWeeksAgo) && entry.getTimestamp().isBefore(now));
    }


    private List<MoodEntry> createMockEntries(UserAccount user) {
        List<MoodEntry> entries = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfLastWeek = now.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfLastWeek = startOfLastWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDateTime twoWeeksAgo = startOfLastWeek.minusWeeks(1);

        // Create 7 entries within the last two weeks
        for (int i = 1; i <= 7; i++) {
            entries.add(new MoodEntry(user, "Happy", 8, startOfLastWeek.plusDays(i), "Had a great day"));
        }

        // Create 13 entries older than two weeks
        for (int i = 0; i < 13; i++) {
            entries.add(new MoodEntry(user, "Neutral", 5, twoWeeksAgo.minusDays(i), "Another day"));
        }

        return entries;
    }

    @Test
    void whenFindByUserAndTimestampBetween_thenRetrieveSevenEntriesFromLastWeek() {
        // Given
        List<MoodEntry> mockEntries = createMockEntriesForOneWeek(user);
        mockEntries.forEach(entry -> entityManager.persist(entry));
        entityManager.flush();

        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        LocalDateTime now = LocalDateTime.now();

        // When
        List<MoodEntry> entriesFromLastWeek = moodEntryRepository.findByUserAndTimestampBetween(user, oneWeekAgo, now);

        // Then
        assertThat(entriesFromLastWeek).hasSize(7);
        assertThat(entriesFromLastWeek)
                .allMatch(entry -> entry.getTimestamp().isAfter(oneWeekAgo) && entry.getTimestamp().isBefore(now));
    }
    private List<MoodEntry> createMockEntriesForOneWeek(UserAccount user) {
        List<MoodEntry> entries = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Create 7 entries within the last week
        for (int i = 0; i < 7; i++) {
            entries.add(new MoodEntry(user, "Happy", 8, startOfThisWeek.plusDays(i), "Good day " + i));
        }

        // Create entries older than one week to test the filtering
        for (int i = 1; i <= 5; i++) {
            entries.add(new MoodEntry(user, "Sad", 4, startOfThisWeek.minusDays(i), "Not so good day " + i));
        }

        return entries;
    }
    @Test
    void whenFindByUserAndTimestampBetween_thenRetrieveEntriesFromLastMonth() {
        // Given
        List<MoodEntry> mockEntries = createMockEntriesForOneMonth(user);
        mockEntries.forEach(entry -> entityManager.persist(entry));
        entityManager.flush();

        LocalDateTime startOfLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime startOfThisMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();

        // When
        List<MoodEntry> entriesFromLastMonth = moodEntryRepository.findByUserAndTimestampBetween(user, startOfLastMonth, startOfThisMonth);

        // Then
        int expectedEntries = 16; // Assuming 4 weeks * 4 entries per week
        assertThat(entriesFromLastMonth).hasSize(expectedEntries);
        assertThat(entriesFromLastMonth).allMatch(entry ->
                !entry.getTimestamp().isBefore(startOfLastMonth) && entry.getTimestamp().isBefore(startOfThisMonth));
    }


    private List<MoodEntry> createMockEntriesForOneMonth(UserAccount user) {
        List<MoodEntry> entries = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime starLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();

        // Assume 4 entries per week for 4 weeks, within the last month
        for (int week = 0; week < 4; week++) {
            for (int day = 0; day < 4; day++) { // 4 entries per week
                entries.add(new MoodEntry(user, "Content", 7, starLastMonth.plusWeeks(week).plusDays(day), "Decent day " + week + " " + day));
            }
        }

        // Create entries older than one month to test the filtering
        for (int i = 1; i <= 5; i++) {
            entries.add(new MoodEntry(user, "Stressed", 3, starLastMonth.minusMonths(1).minusDays(i), "Stressful day " + i));
        }

        return entries;
    }




}
