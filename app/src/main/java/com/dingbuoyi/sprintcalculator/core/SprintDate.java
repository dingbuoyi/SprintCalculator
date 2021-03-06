package com.dingbuoyi.sprintcalculator.core;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import java.util.Set;

public class SprintDate implements Serializable {
    final int year;
    final int month;
    final int dayOfMonth;
    final transient SprintDateHelper dateHelper;

    public SprintDate(final int year, final int month, final int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dateHelper = new SprintDateHelper();
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

    public SprintDate getAvailableEndingDay(Set<SprintDate> holidays, int days) {
        return dateHelper.getAvailableEndDate(this, holidays, days);
    }

    public SprintDate getNextAvailableDay(Set<SprintDate> holidays) {
        return dateHelper.getNextAvailableDate(this, holidays);
    }

    public boolean isWeekend() {
        return dateHelper.isWeekend(this);
    }

    public boolean isPublicHoliday() {
        return dateHelper.isPublicHoliday(this);
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
