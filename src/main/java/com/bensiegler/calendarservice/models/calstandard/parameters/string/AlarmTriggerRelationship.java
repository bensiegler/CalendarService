package com.bensiegler.calendarservice.models.calstandard.parameters.string;

public class AlarmTriggerRelationship extends StringParameter {

    public AlarmTriggerRelationship() {
        super("RELATED");
    }

    public AlarmTriggerRelationship(String startOrEnd) {
        super("RELATED", startOrEnd);
    }

    @Override
    public void setValue(String value) {
        if(value.equals("END") && value.equals("START")) {
            throw new IllegalStateException("Alarm trigger relationship can only be START or END");
        }
        super.setValue(value);
    }
}
