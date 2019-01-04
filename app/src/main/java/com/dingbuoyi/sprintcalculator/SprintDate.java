package com.dingbuoyi.sprintcalculator;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Calendar;
import java.util.Objects;
import java.util.Set;

public class SprintDate {
    final int year;
    final int month;
    final int dayOfMonth;

    public SprintDate(final int year, final int month, final int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public Calendar toCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getYear(), getMonth(), getDayOfMonth());
        return calendar;
    }

    public boolean before(SprintDate endDate) {
        SprintDate startDate = this;
        return startDate.toCalendar().before(endDate.toCalendar());
    }

    public int getTotalDays(SprintDate endDate) {
        DateTime startDateTime = new DateTime(this.toCalendar());
        DateTime endDateTime = new DateTime(endDate.toCalendar());
        return Days.daysBetween(startDateTime, endDateTime).getDays();
    }

    public SprintDate getAvailableEndDate(Set<SprintDate> holidays, int days) {
        if (days == 1) {
            return getAvailableStartDate(holidays);
        } else {
            SprintDate sprintDate = getAvailableStartDate(holidays);

            return sprintDate.getDaysAfter(days).get;
        }
    }

    private SprintDate getAvailableStartDate(Set<SprintDate> holidays) {
        SprintDate sprintDate = new SprintDate(this.getYear(), this.getMonth(), this.getDayOfMonth());
        return getValidDate(sprintDate, holidays);
    }

    private SprintDate getValidDate(SprintDate sprintDate, Set<SprintDate> holidays) {
        if (sprintDate.isWeekend() || sprintDate.isPublicHoliday() || sprintDate.isHoliday(holidays)) {
            return getValidDate(getDaysAfter(sprintDate, 1), holidays);
        } else {
            return sprintDate;
        }
    }

    public SprintDate getDaysAfter(int days) {
        return getDaysAfter(this, days);
    }

    private SprintDate getDaysAfter(SprintDate sprintDate, int days) {
        Calendar calendar = sprintDate.toCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new SprintDate(year, month, day);
    }

    public boolean isWeekend() {
        Calendar calendar = toCalendar();
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPublicHoliday() {
        Calendar calendar = toCalendar();
        if (isChristmas(calendar) || isNewYear(calendar)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHoliday(Set<SprintDate> holidays) {
        return holidays.contains(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SprintDate)) return false;
        SprintDate that = (SprintDate) o;
        return year == that.year &&
                month == that.month &&
                dayOfMonth == that.dayOfMonth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, dayOfMonth);
    }

    @Override
    public String toString() {
        return year + "/" + (month + 1) + "/" + dayOfMonth;
    }

}
