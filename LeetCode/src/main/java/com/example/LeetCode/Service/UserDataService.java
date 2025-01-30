package com.example.LeetCode.Service;

import com.example.LeetCode.Model.UserData;
import com.example.LeetCode.Model.UserDataApiResponse;
import com.example.LeetCode.Repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserDataService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Async
    public CompletableFuture<Void> fetchAndSaveUserData(String username) {
        String apiUrl = "https://leetcode-stats-api.herokuapp.com/" + username;
        UserDataApiResponse apiResponse = restTemplate.getForObject(apiUrl, UserDataApiResponse.class);
        UserData newData = getUserData(username, apiResponse);

        UserData existingData = userDataRepository.findByUsername(username);
        if (existingData != null) {
            if (!existingData.equals(newData)) {
                userDataRepository.save(newData);
            }
        } else {
            userDataRepository.save(newData);
        }

        return CompletableFuture.completedFuture(null);
    }


    private static UserData getUserData(String username, UserDataApiResponse apiResponse) {
        UserData newData = new UserData(
                username,
                apiResponse.getTotalSolved(),
                apiResponse.getEasySolved(),
                apiResponse.getMediumSolved(),
                apiResponse.getHardSolved(),
                apiResponse.getAcceptanceRate(),
                apiResponse.getRanking(),
                apiResponse.getSubmissionCalendar()
        );

        newData.setUsername(username);
        newData.setTotalSolved(apiResponse.getTotalSolved());
        newData.setEasySolved(apiResponse.getEasySolved());
        newData.setMediumSolved(apiResponse.getMediumSolved());
        newData.setHardSolved(apiResponse.getHardSolved());
        newData.setAcceptanceRate(apiResponse.getAcceptanceRate());
        newData.setRanking(apiResponse.getRanking());
        newData.setSubmissionCalendar(apiResponse.getSubmissionCalendar());
        return newData;
    }

    public List<String> clubLeaderBoard() {
        return userDataRepository.clubLeaderBoard();
    }

    public List<String> languageLeaderBoard(String selectedLanguage) {
        return userDataRepository.languageLeaderBoard(selectedLanguage);
    }

    public Map<String, Boolean> hasAttemptedToday(String selectedLanguage) {
        List<Object[]> results = userDataRepository.hasAttemptedToday(selectedLanguage);
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Boolean) result[1],
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Integer> questionsCount(String selectedLanguage) {
        List<Object[]> results = userDataRepository.questionsCount(selectedLanguage);
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Integer) result[1],
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    public List<Boolean> lastSevenDays(String username) {
        List<String> result = userDataRepository.lastSevenDays(username);
        if (!result.isEmpty() && result.getFirst() != null) {
            return Arrays.stream(result.getFirst().split(","))
                    .map(Boolean::parseBoolean)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<String> questionsSolved(String username) {
        List<String> result = userDataRepository.questionsSolved(username);
        if (!result.isEmpty() && result.getFirst() != null) {
            return Arrays.stream(result.getFirst().split(","))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<String> nameAndLanguage(String username) {
        List<String> result = userDataRepository.nameAndLanguage(username);
        if (!result.isEmpty() && result.getFirst() != null) {
            return Arrays.stream(result.getFirst().split(","))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
