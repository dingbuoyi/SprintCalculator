package com.dingbuoyi.sprintcalculator;

import java.util.Set;

public class SprintCalculator {
    final SprintModel sprintModel;
    final SprintDate startDate;
    final SprintDate endDate;
    final Set<SprintDate> holidays;

    public SprintCalculator(final SprintModel sprintModel, final SprintDate startDate, final SprintDate endDate, final Set<SprintDate> holidays) {
        this.sprintModel = sprintModel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.holidays = holidays;
    }

    public void execute() {
        calculator(startDate, endDate);
    }

    private void calculator(SprintDate sprintStartDate, SprintDate sprintEndDate) {
        if (sprintStartDate.before(sprintEndDate)) {
            int totalDays = sprintModel.getTotalDays();
            SprintDate endDate = sprintStartDate.getDaysAfter(totalDays);
            System.out.println("calculator start, start date : " + sprintStartDate.toString() + " , end date : " + endDate.toString());
            calculator(endDate, sprintEndDate);
        } else {
            System.out.println("calculator end");
        }
    }

}
