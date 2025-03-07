package com.example.LeetCode.Controller;

import com.example.LeetCode.Model.*;
import com.example.LeetCode.Service.UserDataService;
import com.example.LeetCode.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDataService userDataService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginCredentials credentials) {
        LoginResponse userResponse = userService.login(credentials);
        System.out.println(userResponse);
        return userResponse != null ? ResponseEntity.ok(userResponse)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/clubLeaderBoard")
    public List<LeaderboardEntry> clubLeaderBoard() {
        return userDataService.getClubLeaderBoard();
    }

    @GetMapping("/languageLeaderBoard/{selectedLanguage}")
    public List<LeaderboardEntry> languageLeaderBoard(@PathVariable String selectedLanguage) {
        return userDataService.getLanguageLeaderBoard(selectedLanguage);
    }

    @GetMapping("/hasAttemptedToday/{selectedLanguage}")
    public Map<String, Boolean> hasAttemptedToday(@PathVariable String selectedLanguage) {
        return userDataService.hasAttemptedToday(selectedLanguage);
    }

    @GetMapping("/lastSevenDays/{username}")
    public List<Boolean> lastSevenDays(@PathVariable String username) {
        return userDataService.lastSevenDays(username);
    }

    @GetMapping("/data/updateAll")
    public ResponseEntity<String> updateAllUsersData() {
        List<String> allUsers = userService.getAllUsernames();
        for (String username : allUsers) {
            userDataService.fetchAndSaveUserData(username);
        }
        return ResponseEntity.ok("Data updated for all users");
    }

    @GetMapping("/data/updateUser/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username) {
        return userDataService.updateUser(username);
    }

    @GetMapping("/questionsSolved/{username}")
    public List<String> questionsSolved(@PathVariable String username) {
        return userDataService.questionsSolved(username);
    }

    @GetMapping("/nameAndLanguage/{username}")
    public List<String> nameAndLanguage(@PathVariable String username) {
        return userDataService.nameAndLanguage(username);
    }

    @GetMapping("/questionsCount/{selectedLanguage}")
    public Map<String, Integer> questionsCount(@PathVariable String selectedLanguage) {
        return userDataService.questionsCount(selectedLanguage);
    }

    @GetMapping("/getUserSocials/{username}")
    public UserProfileDTO getUserSocialsAndProfile(@PathVariable String username) {
        return userDataService.getUserSocials(username);
    }

    @GetMapping("/getUserProfile/{username}")
    public UserProfileDTO getUserProfile(@PathVariable String username) {
        return userDataService.getUserProfile(username);
    }
}
