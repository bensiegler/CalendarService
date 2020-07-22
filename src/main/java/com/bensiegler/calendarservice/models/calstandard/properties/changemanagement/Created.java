package com.bensiegler.calendarservice.models.calstandard.properties.changemanagement;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Created extends Property {
    private DateTime content;

    public Created() {
        super("CREATED");
    }

    public Created(Long timeInMillis) {
        super("CREATED");
        this.content = new DateTime(timeInMillis);
    }

    public Created(ArrayList<UnknownParameter> extras, DateTime dateTime) {
        super("CREATED", extras);
        this.content = dateTime;
    }

    public DateTime getContent() {
        return content;
    }

    public void setContent(DateTime dateTime) {
        this.content = dateTime;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("Content cannot be null");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        setContent(new DateTime(Long.parseLong(content)));
    }

    @Override
    public String retrieveContentAsString() {
        return String.valueOf(content.getContent());
    }
}
