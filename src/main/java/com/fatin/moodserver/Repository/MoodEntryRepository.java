package com.fatin.moodserver.Repository;

import com.fatin.moodserver.Model.MoodEntry;
import com.fatin.moodserver.Model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
    // Custom query methods can be defined here if needed


    List<MoodEntry> findByUserAndTimestampBetween(UserAccount user, LocalDateTime start, LocalDateTime end);

}
