package com.example.LeetCode.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.*;

@Entity
@Table(name = "user_data")
public class UserData {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    private int totalSolved;
    private int easySolved;
    private int mediumSolved;
    private int hardSolved;
    private double acceptanceRate;
    private int ranking;
    private String githubUrl;
    private String twitterUrl;
    private String linkedinUrl;
    private String userAvatar;
    private String school;

    @Column(name = "submission_calendar")
    private List<Boolean> submissionCalendar = new ArrayList<>(30);  // Using List instead of array

    @Column(name = "submitted_today")
    private Boolean submittedToday;

    // No-argument constructor
    public UserData() {
    }

    public UserData(String username, int totalSolved, int easySolved, int mediumSolved, int hardSolved, double acceptanceRate, int ranking, Map<Long, Integer> submissionCalendar, String githubUrl, String twitterUrl, String linkedinUrl, String userAvatar, String school) {

        this.username = username;
        this.totalSolved = totalSolved;
        this.easySolved = easySolved;
        this.mediumSolved = mediumSolved;
        this.hardSolved = hardSolved;
        this.acceptanceRate = acceptanceRate;
        this.ranking = ranking;
        this.setSubmissionCalendar(submissionCalendar);
        this.githubUrl = githubUrl;
        this.twitterUrl = twitterUrl;
        this.linkedinUrl = linkedinUrl;
        this.userAvatar = userAvatar;
        this.school = school;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalSolved() {
        return totalSolved;
    }

    public void setTotalSolved(int totalSolved) {
        this.totalSolved = totalSolved;
    }

    public int getEasySolved() {
        return easySolved;
    }

    public void setEasySolved(int easySolved) {
        this.easySolved = easySolved;
    }


    public int getMediumSolved() {
        return mediumSolved;
    }

    public void setMediumSolved(int mediumSolved) {
        this.mediumSolved = mediumSolved;
    }

    public int getHardSolved() {
        return hardSolved;
    }

    public void setHardSolved(int hardSolved) {
        this.hardSolved = hardSolved;
    }

    public double getAcceptanceRate() {
        return acceptanceRate;
    }

    public void setAcceptanceRate(double acceptanceRate) {
        this.acceptanceRate = acceptanceRate;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public List<Boolean> getSubmissionCalendar() {
        return submissionCalendar;
    }

    public void setSubmissionCalendar(Map<Long, Integer> submissionCalendarMap) {
        List<Boolean> last7DaysSubmission = new ArrayList<>(30); // Using List instead of array
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long todayTimestamp = calendar.getTimeInMillis() / 1000;
        for (int i = 0; i < 30; i++) {
            long dayStartTimestamp = todayTimestamp - (i * 86400); // Subtract i days in seconds

            boolean isSubmitted = submissionCalendarMap.containsKey(dayStartTimestamp);
            last7DaysSubmission.add(isSubmitted);
        }

        submittedToday = last7DaysSubmission.getFirst();
        this.submissionCalendar = last7DaysSubmission;
    }

    public Boolean getSubmittedToday() {
        return submittedToday;
    }

    public void setSubmittedToday(Boolean submittedToday) {
        this.submittedToday = submittedToday;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
