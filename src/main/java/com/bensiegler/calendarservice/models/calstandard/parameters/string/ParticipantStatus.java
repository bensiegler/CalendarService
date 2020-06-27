package com.bensiegler.calendarservice.models.calstandard.parameters.string;

public class ParticipantStatus extends StringParameter {

    public ParticipantStatus() {
        super("PARTSTAT");
    }

    public ParticipantStatus(String value) {
        super("PARTSTAT", value);
    }
}
