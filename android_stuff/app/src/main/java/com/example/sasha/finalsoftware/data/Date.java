package com.example.sasha.finalsoftware.data;

import java.util.Objects;

public class Date {

    private static final int MIN_START_YEAR = 1880;
    private static final int MAX_END_YEAR = 2008;
    private int startYear;
    private int endYear;

    public Date() {
        this.setPeriodTimeFrame(MIN_START_YEAR, MAX_END_YEAR);
    }

    public Date(Date date) {
        this.setPeriodTimeFrame(date.getStartYear(), date.getEndYear());
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setPeriodTimeFrame(int startYear, int endYear) {
        if ((startYear <= endYear) && (startYear >= MIN_START_YEAR) && (endYear <= MAX_END_YEAR)) {
            this.startYear = startYear;
            this.endYear = endYear;
        } else {
            throw new IllegalArgumentException("The value of startYear must be <= endYear");
        }
    }

    @Override
    public boolean equals(Object obj) {
        boolean ret = false;
        Date period = null;

        if (obj instanceof Date) {
            period = (Date) obj;
        } else {
            throw new IllegalArgumentException("The passed in value of obj is not an instance of" +
                    " Period");
        }
        if (period != null
                && (this.getStartYear() == period.getStartYear())
                && (this.getEndYear() == period.getEndYear())) {
            ret = true;
        }

        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.startYear;
        hash = 83 * hash + this.endYear;
        return hash;
    }
}