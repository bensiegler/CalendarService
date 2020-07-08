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
    private ArrayList<Comment> comments;
    private ArrayList<RecurrenceDates> recurrenceDates;
    private ArrayList<TZName> tzNames;

    @Override
    public ArrayList<String> getCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("BEGIN:DAYLIGHT");
        lines.add(Property.toCalStream(dateTimeStart));
        lines.add(Property.toCalStream(tzOffsetTo));
        lines.add(Property.toCalStream(tzOffsetFrom));
        lines.add(Property.toCalStream(recurrenceRule));

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
