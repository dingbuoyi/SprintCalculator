package com.dingbuoyi.sprintcalculator;

import java.util.ArrayList;
import java.util.Set;

public class SprintCalculator {
    final ArrayList<SprintSchedule> sprintScheduleList;
    final SprintModel sprintModel;
    final SprintDate startDate;
    final SprintDate endDate;
    final Set<SprintDate> holidays;


    public SprintCalculator(final SprintModel sprintModel, final SprintDate startDate, final SprintDate endDate, final Set<SprintDate> holidays) {
        this.sprintModel = sprintModel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.holidays = holidays;
        this.sprintScheduleList = new ArrayList<>();
    }

    public ArrayList<SprintSchedule> execute() {
        return calculator(startDate, endDate);
    }

    private ArrayList<SprintSchedule> calculator(SprintDate sprintStartDate, SprintDate sprintEndDate) {
        if (sprintStartDate.before(sprintEndDate)) {
            int totalDays = sprintModel.getTotalDays();
            SprintDate endDate = sprintStartDate.getDaysAfter(totalDays);
            System.out.println("calculator start, start date : " + sprintStartDate.toString() + " , end date : " + endDate.toString());
            SprintSchedule sprintSchedule = new SprintSchedule();
            sprintSchedule.setStartDate(sprintStartDate.toString());
            sprintSchedule.setEndDate(endDate.toString());
            sprintSchedule.setTotalDays(totalDays);
            sprintScheduleList.add(sprintSchedule);
            calculator(endDate, sprintEndDate);
        } else {
            System.out.println("calculator end");
        }
        return sprintScheduleList;
    }

}
