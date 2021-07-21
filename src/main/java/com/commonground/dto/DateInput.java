package com.commonground.dto;

public class DateInput {

    private String startDate;
    private String endDate;

    public  DateInput(){

    }
    public DateInput(String startDate, String endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
