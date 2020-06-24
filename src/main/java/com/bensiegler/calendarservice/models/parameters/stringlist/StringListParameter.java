package com.bensiegler.calendarservice.models.parameters.stringlist;

import com.bensiegler.calendarservice.models.parameters.Parameter;

import java.util.ArrayList;

public abstract class StringListParameter extends Parameter {
    private ArrayList<String> values;

    public StringListParameter(String name) {
        super(name);
    }

    public StringListParameter(String name, ArrayList<String> values) {
        super(name);
        this.values = values;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }


    @Override
    public String toString() {
        String stringRep = super.getName() + "=";

        for(String s: values) {
            stringRep += "\"" + s + "\",";
        }

        //trim last comma off
        return stringRep.substring(0, stringRep.length() - 1);
    }

    @Override
    public String toStringNoName() {
        String stringRep = "";

        for(String s: values) {
            stringRep += "\"" + s + "\",";
        }

        //trim last comma off
        return stringRep.substring(0, stringRep.length() - 1);
    }
}
