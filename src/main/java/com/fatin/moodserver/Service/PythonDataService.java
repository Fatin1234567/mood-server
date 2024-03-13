package com.fatin.moodserver.Service;

import com.fatin.moodserver.Model.AnomalyDetectionResponse;
import com.fatin.moodserver.Model.MoodEntry;
import com.fatin.moodserver.Model.MoodEntryAnomaly;
import com.fatin.moodserver.Model.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PythonDataService {

    private final MoodEntryService moodEntryService;

    public PythonDataService(MoodEntryService moodEntryService){
        this.moodEntryService = moodEntryService;
    }

    public AnomalyDetectionResponse processMoodData(UserAccount user) {
        RestTemplate restTemplate = new RestTemplate();
        String pythonServiceUrl = "http://127.0.0.1:5000/processAnomaly"; // The URL of the Python service

        Map<String, LocalDate> quarterDates = getCurrentQuarterDates();
        LocalDate start = quarterDates.get("start");
        LocalDate end = quarterDates.get("end");

        List<MoodEntry> moodEntries = moodEntryService.getMoodEntriesBetween(user, start.atStartOfDay(), end.atStartOfDay());


        List<MoodEntryAnomaly> requests = moodEntryService.convertToRequestAnomalies(moodEntries);

        Map<String, Object> moodData = new HashMap<>();
        moodData.put("moodEntries", requests);

        return restTemplate.postForObject(pythonServiceUrl, moodData, AnomalyDetectionResponse.class);


    }



    private Map<String, LocalDate> getCurrentQuarterDates() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int currentQuarter = (month - 1) / 3 + 1;
        LocalDate start;
        LocalDate end;

        switch (currentQuarter) {
            case 1:
                start = LocalDate.of(now.getYear(), Month.JANUARY, 1);
                end = LocalDate.of(now.getYear(), Month.MARCH, 31);
                break;
            case 2:
                start = LocalDate.of(now.getYear(), Month.APRIL, 1);
                end = LocalDate.of(now.getYear(), Month.JUNE, 30);
                break;
            case 3:
                start = LocalDate.of(now.getYear(), Month.JULY, 1);
                end = LocalDate.of(now.getYear(), Month.SEPTEMBER, 30);
                break;
            case 4:
                start = LocalDate.of(now.getYear(), Month.OCTOBER, 1);
                end = LocalDate.of(now.getYear(), Month.DECEMBER, 31);
                break;
            default:
                throw new IllegalStateException("Unknown quarter");
        }

        Map<String, LocalDate> dates = new HashMap<>();
        dates.put("start", start);
        dates.put("end", end);

        return dates;
    }
}
