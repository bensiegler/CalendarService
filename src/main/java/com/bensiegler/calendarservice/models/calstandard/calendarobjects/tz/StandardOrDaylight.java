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
    public String retrieveCalStream() throws PropertyException, CalObjectException {
        validate();
        StringBuilder lines = new StringBuilder();
        lines.append("BEGIN:").append(standardOrDaylight).append("\n");

        if(null != dateTimeStart) {
            DateTimeStart betterDateTimeStart = new DateTimeStart(dateTimeStart);
            lines.append(Property.toCalStream(betterDateTimeStart)).append("\n");
        }

        if(null != tzOffsetFrom) {
            TZOffsetFrom betterTzOffsetFrom = new TZOffsetFrom();
            betterTzOffsetFrom.setContentUsingString(tzOffsetFrom);
            lines.append(Property.toCalStream(betterTzOffsetFrom)).append("\n");
        }

        if(null != tzOffsetTo) {
            TZOffsetTo betterTzOffsetTo = new TZOffsetTo();
            betterTzOffsetTo.setContentUsingString(tzOffsetTo);
            lines.append(Property.toCalStream(betterTzOffsetTo)).append("\n");
        }

        if(null != recurrenceRule) {
            RecurrenceRule betterRecurrenceRule = new RecurrenceRule(recurrenceRule);
            lines.append(Property.toCalStream(betterRecurrenceRule)).append("\n");
        }

        if(null != comment) {
            Comment betterComment = new Comment(comment);
            lines.append(Property.toCalStream(betterComment)).append("\n");
        }

        if(null != tzName) {
            TZName betterTzName = new TZName(tzName);
            lines.append(Property.toCalStream(betterTzName)).append("\n");
        }

        lines.append("END:").append(standardOrDaylight).append("\n");
        return lines.toString();
    }

    @Override
    public void validate() throws CalObjectException {
        //should all be good!
    }
}
