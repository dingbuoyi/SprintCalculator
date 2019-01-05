package com.dingbuoyi.sprintcalculator.model;

import android.content.Context;
import com.dingbuoyi.sprintcalculator.R;
import com.dingbuoyi.sprintcalculator.core.SprintDate;
import com.dingbuoyi.sprintcalculator.utils.Constants;

import java.io.Serializable;

public class SprintSchedule implements Serializable {

    public static class Period implements Serializable {
        private final SprintDate startDate;
        private final SprintDate endDate;

        public Period(SprintDate startDate, SprintDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getFormatString(String periodName) {
            if (startDate.equals(endDate)) {
                return periodName + "  " + endDate.toString();
            } else {
                return periodName + "  " + startDate.toString() + "  to  " + endDate.toString();
            }
        }
    }

    private Period workPeriod;
    private Period innovationPeriod;
    private Period retrospectivePeriod;
    private Period demoPeriod;
    private Period planPeriod;

    private int holidays;
    private int totalDays;

    public SprintSchedule() {
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Period getInnovationPeriod() {
        return innovationPeriod;
    }

    public void setInnovationPeriod(Period innovationPeriod) {
        this.innovationPeriod = innovationPeriod;
    }

    public Period getRetrospectivePeriod() {
        return retrospectivePeriod;
    }

    public void setRetrospectivePeriod(Period retrospectivePeriod) {
        this.retrospectivePeriod = retrospectivePeriod;
    }

    public Period getDemoPeriod() {
        return demoPeriod;
    }

    public void setDemoPeriod(Period demoPeriod) {
        this.demoPeriod = demoPeriod;
    }

    public Period getPlanPeriod() {
        return planPeriod;
    }

    public void setPlanPeriod(Period planPeriod) {
        this.planPeriod = planPeriod;
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

    public String getFormatString(Context context) {
        StringBuilder sb = new StringBuilder();
        if (workPeriod != null) {
            sb.append(workPeriod.getFormatString(context.getString(R.string.working_days))).append(Constants.SEPARATOR).append(Constants.SEPARATOR);
        }
        if (innovationPeriod != null) {
            sb.append(innovationPeriod.getFormatString(context.getString(R.string.innovation_days))).append(Constants.SEPARATOR).append(Constants.SEPARATOR);
        }
        if (retrospectivePeriod != null) {
            sb.append(retrospectivePeriod.getFormatString(context.getString(R.string.retrospective_meeting))).append(Constants.SEPARATOR).append(Constants.SEPARATOR);
        }
        if (demoPeriod != null) {
            sb.append(demoPeriod.getFormatString(context.getString(R.string.demo_days))).append(Constants.SEPARATOR).append(Constants.SEPARATOR);
        }
        if (planPeriod != null) {
            sb.append(planPeriod.getFormatString(context.getString(R.string.plan_meeting))).append(Constants.SEPARATOR).append(Constants.SEPARATOR);
        }
        sb.append(context.getString(R.string.total_days)).append(" ").append(getTotalDays())
                .append(Constants.SEPARATOR).append(Constants.SEPARATOR).append(Constants.SEPARATOR);
        return sb.toString();
    }
}
