package com.example.LeetCode.Model;

import java.util.List;

public class StatsEntry {
    private String username;
    private int totalSolved;
    private List<Boolean> submissionCalendar;

    public StatsEntry(String username, int totalSolved, List<Boolean> submissionCalendar) {
        this.username = username;
        this.totalSolved = totalSolved;
        this.submissionCalendar = submissionCalendar;
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

    public List<Boolean> getSubmissionCalendar() {
        return submissionCalendar;
    }

    public void setSubmissionCalendar(List<Boolean> submissionCalendar) {
        this.submissionCalendar = submissionCalendar;
    }
}
