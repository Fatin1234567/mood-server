package com.fatin.moodserver.Service;

import com.fatin.moodserver.Model.MoodEntry;
import com.fatin.moodserver.Model.MoodEntryRequest;
import com.fatin.moodserver.Model.UserAccount;
import com.fatin.moodserver.Repository.MoodEntryRepository;
import com.fatin.moodserver.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MoodEntryService {

    private final MoodEntryRepository moodEntryRepo;

    public MoodEntryService(UserRepository userAccountRepository, MoodEntryRepository moodEntryRepository) {
        this.moodEntryRepo = moodEntryRepository;
    }

    public void saveMoodEntry(UserAccount user, MoodEntryRequest moodEntryRequest) {


        MoodEntry moodEntry = new MoodEntry();
        moodEntry.setUser(user);
        moodEntry.setMood(moodEntryRequest.getMood());
        moodEntry.setIntensity(moodEntryRequest.getIntensity());
        moodEntry.setReason(moodEntryRequest.getReason());
        moodEntry.setTimestamp(moodEntryRequest.getTimestamp());

        moodEntryRepo.save(moodEntry);
    }
}