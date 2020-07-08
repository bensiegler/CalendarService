package com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Comment;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DateTimeStart;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.RecurrenceRule;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence.RecurrenceDates;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZName;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZOffsetFrom;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZOffsetTo;

import java.io.IOException;
import java.util.ArrayList;

public class Daylight extends CalendarObject {
    private DateTimeStart dateTimeStart;
    private TZOffsetTo tzOffsetTo;
    private TZOffsetFrom tzOffsetFrom;
    private RecurrenceRule recurrenceRule;
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<RecurrenceDates> recurrenceDates = new ArrayList<>();
    private ArrayList<TZName> tzNames = new ArrayList<>();

    public DateTimeStart getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(DateTimeStart dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public TZOffsetTo getTzOffsetTo() {
        return tzOffsetTo;
    }

    public void setTzOffsetTo(TZOffsetTo tzOffsetTo) {
        this.tzOffsetTo = tzOffsetTo;
    }

    public TZOffsetFrom getTzOffsetFrom() {
        return tzOffsetFrom;
    }

    public void setTzOffsetFrom(TZOffsetFrom tzOffsetFrom) {
        this.tzOffsetFrom = tzOffsetFrom;
    }

    public RecurrenceRule getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(RecurrenceRule recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<RecurrenceDates> getRecurrenceDates() {
        return recurrenceDates;
    }

    public void setRecurrenceDates(ArrayList<RecurrenceDates> recurrenceDates) {
        this.recurrenceDates = recurrenceDates;
    }

    public ArrayList<TZName> getTzNames() {
        return tzNames;
    }

    public void setTzNames(ArrayList<TZName> tzNames) {
        this.tzNames = tzNames;
    }

    @Override
    public ArrayList<String> getCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("BEGIN:DAYLIGHT");
        ArrayList<Property> fields = new ArrayList<>();
        fields.add(dateTimeStart);
        fields.add(tzOffsetFrom);
        fields.add(tzOffsetTo);
        fields.add(recurrenceRule);

        for(Object obj: fields) {
            if (obj != null) {
                lines.add(Property.toCalStream((Property) obj));
            }
        }

        for(Comment c: comments) {
            lines.add(Property.toCalStream(c));
        }

        for(RecurrenceDates rd: recurrenceDates) {
            lines.add(Property.toCalStream(rd));
        }

        for(TZName tzName: tzNames) {
            lines.add(Property.toCalStream(tzName));
        }

        lines.add("END:DAYLIGHT");
        return lines;
    }

    @Override
    public void validate() throws CalObjectException {

    }
}
