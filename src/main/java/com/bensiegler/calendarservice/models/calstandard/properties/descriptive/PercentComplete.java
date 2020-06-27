package com.bensiegler.calendarservice.models.calstandard.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

public class PercentComplete extends Property {
    private Integer content;

    public PercentComplete() {
        super("PERCENT-COMPLETE");
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        if(content > 100 || content < 0) {
            throw new ArithmeticException("A percentage cannot be greater than 100 or less than 0");
        }
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {

    }
}
