package com.example.LeetCode.Model;

public class LeaderboardEntry {
    private String username;
    private int totalSolved;
    private String userAvatar; // Store image URL or base64

    public LeaderboardEntry(String username, int totalSolved, String userAvatar) {
        this.username = username;
        this.totalSolved = totalSolved;
        this.userAvatar = userAvatar;
    }

    // Getters

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

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}

