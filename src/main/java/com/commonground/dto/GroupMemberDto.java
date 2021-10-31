package com.commonground.dto;

public class GroupMemberDto {

    private String firstName;
    private String lastName;
    private String email;
    private String availablityStartDate;
    private String availabilityEndDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvailablityStartDate() {
        return availablityStartDate;
    }

    public void setAvailablityStartDate(String availablityStartDate) {
        this.availablityStartDate = availablityStartDate;
    }

    public String getAvailabilityEndDate() {
        return availabilityEndDate;
    }

    public void setAvailabilityEndDate(String availabilityEndDate) {
        this.availabilityEndDate = availabilityEndDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
