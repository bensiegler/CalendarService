package com.bensiegler.calendarservice.models.calstandard.parameters;

import java.beans.Transient;

public abstract class Parameter {
    private String name;

    public Parameter() {
    }

    public Parameter(String name) {
        this.name = name;
    }

    @Transient
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public abstract String toStringNoName();


}
