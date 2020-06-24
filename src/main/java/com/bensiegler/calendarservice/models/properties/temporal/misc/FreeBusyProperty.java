package com.bensiegler.calendarservice.models.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.datatypes.Period;
import com.bensiegler.calendarservice.models.parameters.string.FreeBusyType;
import com.bensiegler.calendarservice.models.properties.Property;
import java.util.ArrayList;

public class FreeBusyProperty extends Property {
    private FreeBusyType freeBusyType;
    private ArrayList<Period> content = new ArrayList<>();

    public FreeBusyProperty() {
        super("FREEBUSY");
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

    public void setContent(ArrayList<Period> content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(content.size() == 0) {
            throw new PropertyException("You must include at least one Period");
        }
    }
}
