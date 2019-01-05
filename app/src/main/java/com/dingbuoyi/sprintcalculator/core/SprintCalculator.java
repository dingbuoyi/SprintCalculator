package com.dingbuoyi.sprintcalculator.core;

import android.util.Log;
import com.dingbuoyi.sprintcalculator.model.SprintModel;
import com.dingbuoyi.sprintcalculator.model.SprintSchedule;

import java.util.ArrayList;
import java.util.Set;

public class SprintCalculator {
    private final static String TAG = "SprintCalculator";
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
            SprintSchedule sprintSchedule = new SprintSchedule();
            int periodDays = sprintModel.getPeriodDays();
            SprintDate periodEndingDate = sprintStartDate.getAvailableEndingDay(holidays, periodDays);
            sprintSchedule.setWorkPeriod(new SprintSchedule.Period(sprintStartDate, periodEndingDate));

            if (sprintModel.getInnovationDays() > 0) {
                SprintDate innovationStartDay = periodEndingDate.getNextAvailableDay(holidays);
                periodEndingDate = innovationStartDay.getAvailableEndingDay(holidays, sprintModel.getInnovationDays());
                sprintSchedule.setInnovationPeriod(new SprintSchedule.Period(innovationStartDay, periodEndingDate));
            }

            if (sprintModel.getRetrospectiveDays() > 0) {
                SprintDate retrospectiveStartDay = periodEndingDate.getNextAvailableDay(holidays);
                periodEndingDate = retrospectiveStartDay.getAvailableEndingDay(holidays, sprintModel.getRetrospectiveDays());
                sprintSchedule.setRetrospectivePeriod(new SprintSchedule.Period(retrospectiveStartDay, periodEndingDate));
            }

            if (sprintModel.getDemoDays() > 0) {
                SprintDate demoStartDay = periodEndingDate.getNextAvailableDay(holidays);
                periodEndingDate = demoStartDay.getAvailableEndingDay(holidays, sprintModel.getDemoDays());
                sprintSchedule.setDemoPeriod(new SprintSchedule.Period(demoStartDay, periodEndingDate));
            }

            if (sprintModel.getPlanDays() > 0) {
                SprintDate planStartDay = periodEndingDate.getNextAvailableDay(holidays);
                periodEndingDate = planStartDay.getAvailableEndingDay(holidays, sprintModel.getPlanDays());
                sprintSchedule.setPlanPeriod(new SprintSchedule.Period(planStartDay, periodEndingDate));
            }
            sprintSchedule.setTotalDays(sprintStartDate.getTotalDays(periodEndingDate));
            sprintScheduleList.add(sprintSchedule);

            calculator(periodEndingDate.getNextAvailableDay(holidays), sprintEndDate);
        } else {
            Log.d(TAG, "calculator finished ");
        }
        return sprintScheduleList;
    }

}
