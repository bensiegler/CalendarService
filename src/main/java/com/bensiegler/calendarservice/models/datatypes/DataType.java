package com.bensiegler.calendarservice.models.datatypes;

import com.bensiegler.calendarservice.exceptions.PropertyException;

public abstract class DataType {
    abstract void validate() throws PropertyException;
}
