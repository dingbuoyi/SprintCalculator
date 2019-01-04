package com.dingbuoyi.sprintcalculator;

import java.util.Calendar;

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

    public SprintDate getDaysAfter(int days) {
        Calendar calendar = toCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new SprintDate(year, month, day);
    }

    @Override
    public String toString() {
        return "SprintDate{" +
                "year=" + year +
                ", month=" + month +
                ", dayOfMonth=" + dayOfMonth +
                '}';
    }
}
