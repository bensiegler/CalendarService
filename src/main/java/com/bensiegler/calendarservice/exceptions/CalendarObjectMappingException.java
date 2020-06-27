package com.bensiegler.calendarservice.exceptions;

public class CalendarObjectMappingException extends Exception {
    public CalendarObjectMappingException(String message) {
        super(message);
    }

    public CalendarObjectMappingException(String message, Throwable t) {
        super(message, t);
    }
}
