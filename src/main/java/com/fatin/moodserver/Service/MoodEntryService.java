package com.fatin.moodserver.Service;

import com.fatin.moodserver.Model.MoodEntry;
import com.fatin.moodserver.Model.MoodEntryRequest;
import com.fatin.moodserver.Model.MoodEntryAnomaly;
import com.fatin.moodserver.Model.UserAccount;
import com.fatin.moodserver.Repository.MoodEntryRepository;
import com.fatin.moodserver.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<MoodEntry> getMoodEntriesBetween(UserAccount user, LocalDateTime start, LocalDateTime end) {
        return moodEntryRepo.findByUserAndTimestampBetween(user, start, end);
    }
    public List<MoodEntryAnomaly> convertToRequestAnomalies(List<MoodEntry> moodEntries) {
        return moodEntries.stream().map(entry -> {
            MoodEntryAnomaly anomalyRequest = new MoodEntryAnomaly();
            anomalyRequest.setIntensity(entry.getIntensity());
            anomalyRequest.setReason(entry.getReason());
            anomalyRequest.setTimestamp(entry.getTimestamp().toString()); // Format according to your needs
            anomalyRequest.setMood(entry.getMood());
            return anomalyRequest;
        }).collect(Collectors.toList());
    }


}