package com.dingbuoyi.sprintcalculator.core;

import android.content.Context;
import com.dingbuoyi.sprintcalculator.R;
import com.dingbuoyi.sprintcalculator.model.SprintModel;
import com.dingbuoyi.sprintcalculator.model.SprintSchedule;
import com.dingbuoyi.sprintcalculator.utils.Constants;

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
            SprintDate periodEndingDate = sprintStartDate.getAvailableEndingDay(holidays, periodDays);
            System.out.println("period start day : " + sprintStartDate.toString() + " , period end day : " + periodEndingDate.toString());

            SprintDate innovationStartDay = periodEndingDate.getNextAvailableDay(holidays);
            SprintDate innovationEndingDay = innovationStartDay.getAvailableEndingDay(holidays, sprintModel.getInnovationDays());

            SprintDate retrospectiveStartDay = innovationEndingDay.getNextAvailableDay(holidays);
            SprintDate retrospectiveEndingDay = retrospectiveStartDay.getAvailableEndingDay(holidays, sprintModel.getRetrospectiveDays());

            SprintDate demoStartDay = retrospectiveEndingDay.getNextAvailableDay(holidays);
            SprintDate demoEndingDay = demoStartDay.getAvailableEndingDay(holidays, sprintModel.getDemoDays());

            SprintDate planStartDay = demoEndingDay.getNextAvailableDay(holidays);
            SprintDate planEndingDay = planStartDay.getAvailableEndingDay(holidays, sprintModel.getPlanDays());

            SprintSchedule sprintSchedule = new SprintSchedule();
            sprintSchedule.setWorkPeriod(new SprintSchedule.Period(sprintStartDate, periodEndingDate));
            sprintSchedule.setInnovationPeriod(new SprintSchedule.Period(innovationStartDay, innovationEndingDay));
            sprintSchedule.setRetrospectivePeriod(new SprintSchedule.Period(retrospectiveStartDay, retrospectiveEndingDay));
            sprintSchedule.setDemoPeriod(new SprintSchedule.Period(demoStartDay, demoEndingDay));
            sprintSchedule.setPlanPeriod(new SprintSchedule.Period(planStartDay, planEndingDay));
            sprintSchedule.setTotalDays(sprintStartDate.getTotalDays(planEndingDay));
            sprintScheduleList.add(sprintSchedule);

            calculator(planEndingDay.getNextAvailableDay(holidays), sprintEndDate);
        } else {
            System.out.println("calculator end");
        }
        return sprintScheduleList;
    }

}
