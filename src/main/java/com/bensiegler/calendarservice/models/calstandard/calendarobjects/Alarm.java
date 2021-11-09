package com.bensiegler.calendarservice.models.calstandard.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.alarm.Action;
import com.bensiegler.calendarservice.models.calstandard.properties.alarm.RepeatCount;
import com.bensiegler.calendarservice.models.calstandard.properties.alarm.Trigger;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Attachment;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Description;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Summary;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.UID;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.CustomDuration;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.access.method.P;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Alarm extends CalendarObject{

    private UID uid;

    private Action action;
    private Trigger trigger;

    private Description description;
    private Summary summary;
    private CustomDuration duration;
    private RepeatCount repeat;
    private Attachment attachment;



    @Override
    public String retrieveCalStream() throws PropertyException, CalObjectException {
        validate();
        StringBuilder lines = new StringBuilder();
        lines.append("BEGIN:VALARM").append("\n");
        lines.append(Property.toCalStream(uid));
        lines.append(Property.toCalStream(action)).append("\n");
        lines.append(Property.toCalStream(trigger)).append("\n");



        if(null != duration && null != trigger) {
            lines.append(Property.toCalStream(duration));
            lines.append(Property.toCalStream(trigger));
        }

        if(null != description) {
            lines.append(Property.toCalStream(description));
        }

        if(null != summary) {
            lines.append(Property.toCalStream(summary));
        }

        if(null != attachment) {
            lines.append(Property.toCalStream(attachment));
        }

        lines.append("END:VALARM").append("\n");
        return lines.toString();
    }

    @Override
    public void validate() throws CalObjectException {

        //general rules
        if(null == action) {
            throw new CalObjectException("Action on alarm " + uid.getContent() + "cannot be null");
        }

        if(null == trigger) {
            throw new CalObjectException("Trigger on alarm " + uid.getContent() + "cannot be null");
        }

        if(null != duration && null == repeat) {
            throw new CalObjectException("If a duration is specified a repeat count must also be specified");
        }else if (null == duration && null != repeat) {
            throw new CalObjectException("If a repeat count is specified a duration must also be specified");
        }

        //action specific rules
        if(action.getContent().equalsIgnoreCase("AUDIO")) {
            if(null != summary) {
                throw new CalObjectException("Cannot include a summary in an audio alarm");
            }

            if(null != description) {
                throw new CalObjectException("Cannot include a description in an audio alarm");
            }
        }else if(action.getContent().equalsIgnoreCase("DISPLAY")) {
            if(null == description) {
                throw new CalObjectException("You must include a description on a visual alarm");
            }

            if(null != summary) {
                throw new CalObjectException("Cannot include a summary in an audio alarm");
            }

            if(null != attachment) {
                throw new CalObjectException("Cannot include a attachment in an audio alarm");
            }
        }else if(action.getContent().equalsIgnoreCase("EMAIL")) {
            throw new CalObjectException("Email alarms are not currently available");
        }
    }
}
