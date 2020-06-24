package com.bensiegler.calendarservice.models.parameters.string;

public class ParticipantStatus extends StringParameter {

    public ParticipantStatus() {
        super("PARTSTAT");
    }

    public ParticipantStatus(String value) {
        super("PARTSTAT", value);
    }
}
