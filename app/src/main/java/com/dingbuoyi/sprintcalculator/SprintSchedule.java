package com.dingbuoyi.sprintcalculator;

import java.io.Serializable;

public class SprintSchedule implements Serializable {

    private String startDate;
    private String endDate;
    private int holidays;
    private int totalDays;

    public SprintSchedule(){
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

    public int getHolidays() {
        return holidays;
    }

    public void setHolidays(int holidays) {
        this.holidays = holidays;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    @Override
    public String toString() {
        return "SprintSchedule{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", holidays=" + holidays +
                ", totalDays=" + totalDays +
                '}';
    }
}
