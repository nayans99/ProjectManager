package com.example.android.project;

public class ModelUsers {
    String Role, UserName;

    public ModelUsers() {

    }

    public ModelUsers(String userName, String role) {
    }

    public ModelUsers(String userName) {
        UserName = userName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
