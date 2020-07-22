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
    private String standardOrDaylight;

    @NotNull
    private Long dateTimeStart;

    @NotNull
    private String tzOffsetTo;

    @NotNull
    private String tzOffsetFrom;

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

    public long getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(long dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public String getTzOffsetTo() {
        return tzOffsetTo;
    }

    public void setTzOffsetTo(String tzOffsetTo) {
        this.tzOffsetTo = tzOffsetTo;
    }

    public String getTzOffsetFrom() {
        return tzOffsetFrom;
    }

    public void setTzOffsetFrom(String tzOffsetFrom) {
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

    public String getStandardOrDaylight() {
        return standardOrDaylight;
    }

    public void setStandardOrDaylight(String standardOrDaylight) {
        this.standardOrDaylight = standardOrDaylight;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDateTimeStart(Long dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    @Override
    public ArrayList<String> retrieveCalStream() throws PropertyException, CalObjectException {
        validate();
        ArrayList<String> lines = new ArrayList<>();
        lines.add("BEGIN:" + standardOrDaylight);

        if(null != dateTimeStart) {
            DateTimeStart betterDateTimeStart = new DateTimeStart(dateTimeStart);
            lines.add(Property.toCalStream(betterDateTimeStart));
        }

        if(null != tzOffsetFrom) {
            TZOffsetFrom betterTzOffsetFrom = new TZOffsetFrom();
            betterTzOffsetFrom.setContentUsingString(tzOffsetFrom);
            lines.add(Property.toCalStream(betterTzOffsetFrom));
        }

        if(null != tzOffsetTo) {
            TZOffsetTo betterTzOffsetTo = new TZOffsetTo();
            betterTzOffsetTo.setContentUsingString(tzOffsetTo);
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
