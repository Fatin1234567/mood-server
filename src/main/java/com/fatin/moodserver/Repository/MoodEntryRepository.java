package com.fatin.moodserver.Repository;

import com.fatin.moodserver.Model.MoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
    // Custom query methods can be defined here if needed
}
