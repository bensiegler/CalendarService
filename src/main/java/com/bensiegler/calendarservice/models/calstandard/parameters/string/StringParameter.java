package com.bensiegler.calendarservice.models.calstandard.parameters.string;

import com.bensiegler.calendarservice.models.calstandard.parameters.Parameter;

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
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toStringNoName() {
        return "=\"" + value + "\"";
    }

    @Override
    public String toString() {
        return super.getName() + "=\"" + value + "\"";
    }


}
