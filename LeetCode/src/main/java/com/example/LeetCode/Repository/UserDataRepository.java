package com.example.LeetCode.Repository;

import com.example.LeetCode.Model.LeaderboardEntry;
import com.example.LeetCode.Model.UserData;
import com.example.LeetCode.Model.UserProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, String> {
    UserData findByUsername(String username);

    @Query("SELECT new com.example.LeetCode.Model.LeaderboardEntry(ud.username, ud.totalSolved, ud.userAvatar) " +
            "FROM UserData ud " +
            "ORDER BY ud.totalSolved DESC")
    List<LeaderboardEntry> clubLeaderBoard();

    @Query("SELECT new com.example.LeetCode.Model.LeaderboardEntry(ud.username, ud.totalSolved, ud.userAvatar) " +
            "FROM UserData ud " +
            "JOIN Users u ON u.username = ud.username " +
            "WHERE u.selectedLanguage = :selectedLanguage " +
            "ORDER BY ud.totalSolved DESC")
    List<LeaderboardEntry> languageLeaderBoard(@Param("selectedLanguage") String selectedLanguage);

    @Query("SELECT ud.username, ud.submittedToday FROM UserData ud JOIN Users u ON u.username = ud.username WHERE u.selectedLanguage = :selectedLanguage ORDER BY ud.submittedToday")
    List<Object[]> hasAttemptedToday(@Param("selectedLanguage") String selectedLanguage);

    @Query("SELECT ud.username, ud.totalSolved FROM UserData ud JOIN Users u ON u.username = ud.username WHERE u.selectedLanguage = :selectedLanguage ORDER BY ud.totalSolved ASC")
    List<Object[]> questionsCount(@Param("selectedLanguage") String selectedLanguage);

    @Query("SELECT u.submissionCalendar FROM UserData u WHERE u.username = :username")
    List<String> lastSevenDays(@Param("username") String username);

    @Query("SELECT ud.totalSolved, ud.easySolved, ud.mediumSolved, ud.hardSolved, ud.ranking, ud.acceptanceRate FROM UserData ud WHERE ud.username = :username")
    List<String> questionsSolved(@Param("username") String username);

    @Query("SELECT u.name, u.selectedLanguage FROM Users u WHERE u.username = :username")
    List<String> nameAndLanguage(@Param("username") String username);

    @Query("SELECT new com.example.LeetCode.Model.UserProfileDTO(u.githubUrl, u.twitterUrl, u.linkedinUrl, u.school) FROM UserData u WHERE u.username = :username")
    UserProfileDTO getUserSocials(@Param("username") String username);

    @Query("SELECT new com.example.LeetCode.Model.UserProfileDTO(u.userAvatar) FROM UserData u WHERE u.username = :username")
    UserProfileDTO getUserProfile(@Param("username") String username);

}
