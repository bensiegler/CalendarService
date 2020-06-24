package com.bensiegler.calendarservice.models.properties.temporal.misc;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.datatypes.Date;
import com.bensiegler.calendarservice.models.datatypes.DateTime;
import com.bensiegler.calendarservice.models.datatypes.Period;
import com.bensiegler.calendarservice.models.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.temporal.TemporalProperty;

import java.lang.reflect.Field;
import java.util.ArrayList;

//note this property does not extend DateTimeProperty or DateProperty since it can also be of type period and that complicates things
public class RecurrenceDates extends TemporalProperty {
    private ValueType valueType;
    private TimeZoneIdentifier timeZoneIdentifier;
    private ArrayList<Period> periods;
    private ArrayList<Date> dates;
    private ArrayList<DateTime> dateTimes;

    public RecurrenceDates() {
        super("RDATE");
    }

    @Override
    protected Field getContentField() throws NoSuchFieldException {
        if(valueType.getName().equals("PERIOD")) {
            return this.getClass().getDeclaredField("periods");
        }else if(valueType.getName().equals("DATE")) {
            return this.getClass().getDeclaredField("dates");
        }else{
            return this.getClass().getDeclaredField("dateTimes");
        }
    }

    @Override
    protected Field[] getNonContentFields() throws NoSuchFieldException {
        Class<?> clazz = this.getClass();
        return new Field[]{clazz.getDeclaredField("valueType"), clazz.getDeclaredField("timeZoneIdentifier")};
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(String valueTypeString) throws PropertyException {
        if(!valueTypeString.equals("PERIOD") && !valueTypeString.equals("DATE") && !valueTypeString.equals("DATE-TIME")) {
            throw new PropertyException("ValueType may only be DATE, PERIOD or DATE-TIME");
        }
        this.valueType = new ValueType(valueTypeString);
    }

    public TimeZoneIdentifier getTimeZoneIdentifier() {
        return timeZoneIdentifier;
    }

    public void setTimeZoneIdentifier(TimeZoneIdentifier timeZoneIdentifier) {
        this.timeZoneIdentifier = timeZoneIdentifier;
    }

    public ArrayList<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(ArrayList<Period> periods) {
        this.periods = periods;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public ArrayList<DateTime> getDateTimes() {
        return dateTimes;
    }

    public void setDateTimes(ArrayList<DateTime> dateTimes) {
        this.dateTimes = dateTimes;
    }

    @Override
    public void validate() throws PropertyException {
        //this class is validated within the getContentField method
    }
}
