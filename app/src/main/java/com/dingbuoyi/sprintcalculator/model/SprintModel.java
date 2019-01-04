package com.dingbuoyi.sprintcalculator.model;

import java.io.Serializable;

public class SprintModel implements Serializable {
    private final static int DAYS_OF_WEEK = 7;
    private int weeks;
    private int retrospectiveDays;
    private int planDays;
    private int demoDays;
    private int innovationDays;

    public SprintModel() {
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public int getRetrospectiveDays() {
        return retrospectiveDays;
    }

    public void setRetrospectiveDays(int retrospectiveDays) {
        this.retrospectiveDays = retrospectiveDays;
    }

    public int getPlanDays() {
        return planDays;
    }

    public void setPlanDays(int planDays) {
        this.planDays = planDays;
    }

    public int getDemoDays() {
        return demoDays;
    }

    public void setDemoDays(int demoDays) {
        this.demoDays = demoDays;
    }

    public int getInnovationDays() {
        return innovationDays;
    }

    public void setInnovationDays(int innovationDays) {
        this.innovationDays = innovationDays;
    }

    public int getPeriodDays() {
        return weeks * DAYS_OF_WEEK - 1;
    }

    @Override
    public String toString() {
        return "SprintModel{" +
                "weeks=" + weeks +
                ", retrospectiveDays=" + retrospectiveDays +
                ", planDays=" + planDays +
                ", demoDays=" + demoDays +
                ", innovationDays=" + innovationDays +
                '}';
    }
}
