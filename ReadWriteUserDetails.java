package com.example.mobileapp;

public class ReadWriteUserDetails {
    private String fullName;
    private String gender;
    private String role;
    private String additionalField; // Add this if needed

    // Default constructor required for calls to DataSnapshot.getValue(ReadWriteUserDetails.class)
    public ReadWriteUserDetails() {
    }

    // Constructor to initialize all fields
    public ReadWriteUserDetails(String fullName, String gender, String role, String additionalField) {
        this.fullName = fullName;
        this.gender = gender;
        this.role = role;
        this.additionalField = additionalField; // Adjust as necessary
    }

    // Getter and setter methods
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdditionalField() {
        return additionalField;
    }

    public void setAdditionalField(String additionalField) {
        this.additionalField = additionalField;
    }
}
