package com.bensiegler.calendarservice.models.calstandard.properties.alarm;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.AlarmTriggerRelationship;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;

public class Trigger extends Property {
    private ValueType valueType = new ValueType();
    private AlarmTriggerRelationship alarmTriggerRelationship;
    private Duration duration;
    private DateTime content;

//    public Trigger() {
//        super("TRIGGER");
//    }


    public Trigger(Long dateTime) {
        super("TRIGGER");
        this.content = new DateTime(dateTime);
    }

    public Trigger(Duration duration) {
        super("TRIGGER");
        this.duration = duration;
    }

    public Trigger(String name, ArrayList<UnknownParameter> extras, ValueType valueType,
                   AlarmTriggerRelationship alarmTriggerRelationship, Duration duration, DateTime dateTime) {
        super("TRIGGER", extras);
        this.valueType = valueType;
        this.alarmTriggerRelationship = alarmTriggerRelationship;
        this.duration = duration;
        this.content = dateTime;
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
    public Field retrieveContentField() throws NoSuchFieldException {
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
    public Field[] retrieveNonContentFields() throws NoSuchFieldException {
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
    public String retrieveContentAsString() {
        return String.valueOf(content.getContent());
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
