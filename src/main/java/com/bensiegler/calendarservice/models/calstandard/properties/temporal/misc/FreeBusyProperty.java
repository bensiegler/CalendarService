package com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Period;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.FreeBusyType;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import org.springframework.security.access.method.P;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;

public class FreeBusyProperty extends Property {
    private FreeBusyType freeBusyType;
    private ArrayList<Period> content = new ArrayList<>();

    public FreeBusyProperty() {
        super("FREEBUSY");
    }

    public FreeBusyProperty(String content) {
        super("FREEBUSY");
        String[] periods = content.split(",");
        ArrayList<Period> periodArray = new ArrayList<>();

        for(String s: periods) {
            Long dateTime = Long.parseLong(s.substring(0, s.indexOf("/")));
            Long duration = Long.parseLong(s.substring(s.indexOf("/")));

            periodArray.add(new Period(dateTime, duration));
        }

        this.content = periodArray;
    }

    public FreeBusyProperty(ArrayList<UnknownParameter> extras, FreeBusyType freeBusyType, ArrayList<Period> content) {
        super("FREEBUSY", extras);
        this.freeBusyType = freeBusyType;
        this.content = content;
    }

    public FreeBusyType getFreeBusyType() {
        return freeBusyType;
    }

    public void setFreeBusyType(FreeBusyType freeBusyType) {
        this.freeBusyType = freeBusyType;
    }

    public ArrayList<Period> getContent() {
        return content;
    }

    public void setContent(Period[] content) {
        this.content = new ArrayList<>(Arrays.asList(content));
    }

    @Override
    public void validate() throws PropertyException {
        if(content.size() == 0) {
            throw new PropertyException("You must include at least one Period");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        String[] stringArr = content.split(",");
        Period[] periods = new Period[stringArr.length];
        String currentString;
        Long dateTime;
        Long duration;
        for(int i = 0; i < stringArr.length; i++) {
            currentString = stringArr[i];
            dateTime = Long.parseLong(currentString.substring(0, currentString.indexOf("/")));
            duration = Long.parseLong(currentString.substring(currentString.indexOf("/")));
            periods[i] = new Period(dateTime, duration);
        }

        this.content = new ArrayList<>(Arrays.asList(periods));
    }

    @Override
    public String retrieveContentAsString() {
        String contentString = "";

        for(Period p: content) {
            contentString += p.toString() + ",";
        }

        return contentString.substring(0, contentString.length() - 1);
    }
}
