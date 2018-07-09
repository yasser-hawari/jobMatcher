package com.sj.jobMatcher.model;


public class Availability {

    private Integer dayIndex;

    private String title;

    public Availability() {
    }

    public Availability(Integer dayIndex, String title) {
        this.dayIndex = dayIndex;
        this.title = title;
    }

    // Getters and Setters

    public Integer getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(Integer dayIndex) {
        this.dayIndex = dayIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
