package com.fatin.moodserver.Service;

import com.fatin.moodserver.Model.MoodEntry;
import com.fatin.moodserver.Model.UserAccount;
import com.fatin.moodserver.Repository.MoodEntryRepository;
import com.fatin.moodserver.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MoodEntryService {

    private final UserRepository userAccountRepository;
    private final MoodEntryRepository moodEntryRepository;

    public MoodEntryService(UserRepository userAccountRepository, MoodEntryRepository moodEntryRepository) {
        this.userAccountRepository = userAccountRepository;
        this.moodEntryRepository = moodEntryRepository;
    }

    public void saveMoodEntry(Long userId, MoodEntry moodEntry) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Associate moodEntry with the user
        moodEntry.setUser(user);

        // Save the moodEntry (either through the user or directly)
        // Depending on the cascade type in your UserAccount entity,
        // saving user may also save moodEntry
        moodEntryRepository.save(moodEntry);
    }
}