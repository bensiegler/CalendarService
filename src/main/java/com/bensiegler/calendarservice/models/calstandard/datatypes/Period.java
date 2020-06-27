package com.bensiegler.calendarservice.models.calstandard.datatypes;

import com.bensiegler.calendarservice.exceptions.PropertyException;

import java.time.Duration;
import java.time.Instant;

public class Period extends DataType {
    private DateTime dateTimeStart;
    private Duration duration;

    public Period() {
    }

    public Period(Long start, Long end) {
        setInterval(start, end);
    }

    public Period(DateTime dateTime, Duration duration) {
        this.dateTimeStart = dateTime;
        this.duration = duration;
    }

    public DateTime getDateTime() {
        return dateTimeStart;
    }

    public void setDateTime(Long timeInMillis) {
        this.dateTimeStart = new DateTime(timeInMillis);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setInterval(Long start, Long end) {
        setDateTime(start);
        Instant startInstant = Instant.ofEpochMilli(start);
        Instant endInstant = Instant.ofEpochMilli(end);
        setDuration(Duration.between(startInstant, endInstant));
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        try {
            validate();
        }catch (PropertyException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return dateTimeStart.toString() + "/" + duration.toString();
    }

    @Override
    void validate() throws PropertyException {
        if(null == dateTimeStart) {
            throw new PropertyException("DateTime in " +  Period.class + " cannot be null");
        }else if(null == duration) {
            throw new PropertyException("Duration in " + Period.class + " cannot be null");
        }
    }
}
