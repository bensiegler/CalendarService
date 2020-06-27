package com.bensiegler.calendarservice.models.calstandard.datatypes;

import com.bensiegler.calendarservice.exceptions.PropertyException;

public abstract class DataType {
    abstract void validate() throws PropertyException;
}
