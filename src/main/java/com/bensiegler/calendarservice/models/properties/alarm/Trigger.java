package com.bensiegler.calendarservice.models.properties.alarm;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.datatypes.DateTime;
import com.bensiegler.calendarservice.models.parameters.string.AlarmTriggerRelationship;
import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.Property;

import java.lang.reflect.Field;
import java.time.Duration;

public class Trigger extends Property {
    private ValueType valueType = new ValueType();
    private AlarmTriggerRelationship alarmTriggerRelationship;
    private Duration duration;
    private DateTime dateTime;

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

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    protected Field getContentField() throws NoSuchFieldException {
        if(null != alarmTriggerRelationship) {
            return this.getClass().getDeclaredField("duration");
        }

        if(null == dateTime) {
            return this.getClass().getDeclaredField("duration");
        }else{
            return this.getClass().getDeclaredField("dateTime");
        }
    }

    @Override
    protected Field[] getNonContentFields() throws NoSuchFieldException {
        if(null != alarmTriggerRelationship) {
            valueType.setValue("DURATION");
        }else if(null == dateTime) {
            valueType.setValue("DURATION");
        }else{
            valueType.setValue("DATE-TIME");
        }

        return new Field[]{this.getClass().getDeclaredField("valueType"),
                this.getClass().getDeclaredField("alarmTriggerRelationship")};
    }

    @Override
    public void validate() throws PropertyException {
        if(null != alarmTriggerRelationship && null == duration) {
            throw new PropertyException("If alarm trigger relationship is specified a duration must also be specified.");
        }

        if(null == duration && null == dateTime) {
            throw new PropertyException("You must either specify a duration or a dateTime");
        }


    }
}
