package com.example.LeetCode.Model;

public class LoginResponse {
    private String name;
    private String selectedLanguage;
    private String username;
    private String token;

    public LoginResponse(String name, String selectedLanguage, String username, String token) {
        this.name = name;
        this.selectedLanguage = selectedLanguage;
        this.username = username;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

