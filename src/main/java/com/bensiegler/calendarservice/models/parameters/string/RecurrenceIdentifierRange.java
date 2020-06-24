package com.bensiegler.calendarservice.models.parameters.string;

public class RecurrenceIdentifierRange extends StringParameter {

    public RecurrenceIdentifierRange() {
        super("RANGE");
    }

    public RecurrenceIdentifierRange(String value) {
        super("RANGE", value);
    }
}
