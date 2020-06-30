package com.bensiegler.calendarservice.models.calstandard.properties.alarm;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.AlarmTriggerRelationship;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.lang.reflect.Field;
import java.time.Duration;

public class Trigger extends Property {
    private ValueType valueType = new ValueType();
    private AlarmTriggerRelationship alarmTriggerRelationship;
    private Duration duration;
    private DateTime content;

    public Trigger() {
        super("TRIGGER");
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public AlarmTriggerRelationship getAlarmTriggerRelationship() {
        return alarmTriggerRelationship;
    }

    public void setAlarmTriggerRelationship(AlarmTriggerRelationship alarmTriggerRelationship) {
        this.alarmTriggerRelationship = alarmTriggerRelationship;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public DateTime getContent() {
        return content;
    }

    public void setContent(Long timeInMillis) {
        this.content = new DateTime(timeInMillis);
    }

    @Override
    protected Field getContentField() throws NoSuchFieldException {
        if(null != alarmTriggerRelationship) {
            return this.getClass().getDeclaredField("duration");
        }

        if(null == content) {
            return this.getClass().getDeclaredField("duration");
        }else{
            return this.getClass().getDeclaredField("content");
        }
    }

    @Override
    protected Field[] getNonContentFields() throws NoSuchFieldException {
        if(null != alarmTriggerRelationship) {
            valueType.setValue("DURATION");
        }else if(null == content) {
            valueType.setValue("DURATION");
        }else{
            valueType.setValue("DATE-TIME");
        }

        return new Field[]{this.getClass().getDeclaredField("valueType"),
                this.getClass().getDeclaredField("alarmTriggerRelationship")};
    }

    @Override
    public void setContentUsingString(String content) {
        setContent(Long.parseLong(content));
    }

    @Override
    public void validate() throws PropertyException {
        if(null != alarmTriggerRelationship && null == duration) {
            throw new PropertyException("If alarm trigger relationship is specified a duration must also be specified.");
        }

        if(null == duration && null == content) {
            throw new PropertyException("You must either specify a duration or a dateTime");
        }


    }
}
