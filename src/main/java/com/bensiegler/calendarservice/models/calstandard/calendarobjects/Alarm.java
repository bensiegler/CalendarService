package com.bensiegler.calendarservice.models.calstandard.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.alarm.Action;
import com.bensiegler.calendarservice.models.calstandard.properties.alarm.Trigger;
import jdk.jfr.Name;
import org.springframework.security.access.method.P;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

//@Entity
//@Table(name = "alarms")
public class Alarm extends CalendarObject{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    private String action;

    @NotNull
    private String trigger;

    private String description;

    private String summary;

    private String duration;

    private String repeat;

    private String attach;



    @Override
    public String retrieveCalStream() throws PropertyException {
        StringBuilder lines = new StringBuilder();
        lines.append("BEGIN:VALARM").append("\n");
        lines.append(Property.toCalStream(new Action(action)));

        try {
            lines.append(Property.toCalStream(new Trigger(Long.parseLong(trigger)))).append("\n");
        }catch (NumberFormatException e) {
            //trigger is in a duration format as it could not be parsed
            lines.append(Property.toCalStream(new Trigger(Duration.ofMillis(Long.parseLong(trigger))))).append("\n");
        }


        lines.append("END:VALARM").append("\n");
        return lines.toString();
    }

    @Override
    public void validate() throws CalObjectException {

    }
}
