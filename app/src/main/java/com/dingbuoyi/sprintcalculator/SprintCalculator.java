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
            int periodDays = sprintModel.getPeriodDays();
            SprintDate periodEndDate = sprintStartDate.getDaysAfter(periodDays);
            System.out.println("period start day : " + sprintStartDate.toString() + " , period end day : " + periodEndDate.toString());
            SprintDate innovationEndDay = periodEndDate.getDaysAfter(1).getAvailableEndDate(holidays, sprintModel.getInnovationDays());
            System.out.println("innovation end day : " + innovationEndDay.toString());
            SprintDate retrospectiveEndDay = innovationEndDay.getDaysAfter(1).getAvailableEndDate(holidays, sprintModel.getRetrospectiveDays());
            System.out.println("retrospective end day : " + retrospectiveEndDay.toString());
            SprintDate demoEndDay = retrospectiveEndDay.getDaysAfter(1).getAvailableEndDate(holidays, sprintModel.getDemoDays());
            System.out.println("demo end day : " + demoEndDay.toString());
            SprintDate planEndDay = demoEndDay.getDaysAfter(1).getAvailableEndDate(holidays, sprintModel.getPlanDays());
            System.out.println("plan end day : " + planEndDay.toString());

            SprintSchedule sprintSchedule = new SprintSchedule();
            sprintSchedule.setStartDate(sprintStartDate.toString());
            sprintSchedule.setEndDate(planEndDay.toString());
            sprintSchedule.setTotalDays(sprintStartDate.getTotalDays(planEndDay));
            sprintScheduleList.add(sprintSchedule);

            calculator(planEndDay.getDaysAfter(1).getAvailableEndDate(holidays, 1), sprintEndDate);
        } else {
            System.out.println("calculator end");
        }
        return sprintScheduleList;
    }

}
