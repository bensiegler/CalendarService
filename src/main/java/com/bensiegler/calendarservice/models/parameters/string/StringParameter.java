package com.bensiegler.calendarservice.models.parameters.string;

import com.bensiegler.calendarservice.models.parameters.Parameter;

public abstract class StringParameter extends Parameter {
    private String value;

    public StringParameter() {
    }

    public StringParameter(String name) {
        super(name);
    }

    public StringParameter(String name, String value) {
        super(name);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean complete() {
        if(null == super.getName() || null == value) {
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return super.getName() + "=\"" + value + "\"";
    }

    public String toStringNoName() {
        return "=\"" + value + "\"";
    }
}
