package com.dingbuoyi.sprintcalculator.core;

import java.util.Calendar;
import java.util.Set;

public final class SprintDateHelper {

    public SprintDate getValidDate(SprintDate sprintDate, Set<SprintDate> holidays) {
        if (isWeekend(sprintDate) || isPublicHoliday(sprintDate) || isHoliday(sprintDate, holidays)) {
            return getValidDate(getDaysAfter(sprintDate, 1), holidays);
        } else {
            return sprintDate;
        }
    }

    public SprintDate getDaysAfter(SprintDate sprintDate, int days) {
        Calendar calendar = sprintDate.toCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new SprintDate(year, month, day);
    }

    protected boolean isWeekend(SprintDate sprintDate) {
        Calendar calendar = sprintDate.toCalendar();
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isPublicHoliday(SprintDate sprintDate) {
        Calendar calendar = sprintDate.toCalendar();
        if (isChristmas(calendar) || isNewYear(calendar)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isHoliday(SprintDate sprintDate, Set<SprintDate> holidays) {
        return holidays.contains(sprintDate);
    }

    private boolean isChristmas(Calendar calendar) {
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) == 25) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNewYear(Calendar calendar) {
        if (calendar.get(Calendar.MONTH) == Calendar.JANUARY && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        } else {
            return false;
        }
    }

}
