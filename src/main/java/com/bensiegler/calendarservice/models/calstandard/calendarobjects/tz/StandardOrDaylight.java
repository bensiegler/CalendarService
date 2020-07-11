package com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Comment;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DateTimeStart;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.RecurrenceRule;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZName;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZOffsetFrom;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZOffsetTo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;

@Entity
public class StandardOrDaylight extends CalendarObject {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "timezone_id")
    private Integer timezoneId;

    @NotNull
    private String standardOrDaylight;

    @NotNull
    private Long dateTimeStart;

    @NotNull
    private Integer tzOffsetTo;

    @NotNull
    private Integer tzOffsetFrom;

    @NotNull
    private String recurrenceRule;

    @NotNull
    private String tzName;

    private String comment;
    //note that there are no recurrenceDates here


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(int timezoneId) {
        this.timezoneId = timezoneId;
    }

    public long getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(long dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public int getTzOffsetTo() {
        return tzOffsetTo;
    }

    public void setTzOffsetTo(int tzOffsetTo) {
        this.tzOffsetTo = tzOffsetTo;
    }

    public int getTzOffsetFrom() {
        return tzOffsetFrom;
    }

    public void setTzOffsetFrom(int tzOffsetFrom) {
        this.tzOffsetFrom = tzOffsetFrom;
    }

    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(String recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public String getTzName() {
        return tzName;
    }

    public void setTzName(String tzName) {
        this.tzName = tzName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public ArrayList<String> getCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException {
        validate();
        ArrayList<String> lines = new ArrayList<>();
        lines.add("BEGIN:" + standardOrDaylight);

        if(null != dateTimeStart) {
            DateTimeStart betterDateTimeStart = new DateTimeStart(dateTimeStart);
            lines.add(Property.toCalStream(betterDateTimeStart));
        }

        if(null != tzOffsetFrom) {
            TZOffsetFrom betterTzOffsetFrom = new TZOffsetFrom();
            if(tzOffsetFrom < 0) {
                betterTzOffsetFrom.setNegativeOffset(tzOffsetFrom / 100, tzOffsetFrom % 100);
            }else {
                betterTzOffsetFrom.setPositiveOffset(tzOffsetFrom / 100, tzOffsetFrom % 100);
            }
            lines.add(Property.toCalStream(betterTzOffsetFrom));
        }

        if(null != tzOffsetTo) {
            TZOffsetTo betterTzOffsetTo = new TZOffsetTo();
            if(tzOffsetTo < 0) {
                betterTzOffsetTo.setNegativeOffset(tzOffsetTo / 100, tzOffsetTo % 100);
            }else {
                betterTzOffsetTo.setPositiveOffset(tzOffsetTo / 100, tzOffsetTo % 100);
            }
            lines.add(Property.toCalStream(betterTzOffsetTo));
        }

        if(null != recurrenceRule) {
            RecurrenceRule betterRecurrenceRule = new RecurrenceRule(recurrenceRule);
            lines.add(Property.toCalStream(betterRecurrenceRule));
        }

        if(null != comment) {
            Comment betterComment = new Comment(comment);
            lines.add(Property.toCalStream(betterComment));
        }

        if(null != tzName) {
            TZName betterTzName = new TZName(tzName);
            lines.add(Property.toCalStream(betterTzName));
        }

        lines.add("END:" + standardOrDaylight);
        return lines;
    }

    @Override
    public void validate() throws CalObjectException {
        //should all be good!
    }
}
