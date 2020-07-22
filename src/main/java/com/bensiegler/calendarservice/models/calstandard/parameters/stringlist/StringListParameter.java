package com.bensiegler.calendarservice.models.calstandard.parameters.stringlist;

import com.bensiegler.calendarservice.models.calstandard.parameters.Parameter;

import java.util.ArrayList;

public abstract class StringListParameter extends Parameter {
    private ArrayList<String> value;

    public StringListParameter(String name) {
        super(name);
    }

    public StringListParameter(String name, ArrayList<String> value) {
        super(name);
        this.value = value;
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public void setValue(ArrayList<String> value) {
        this.value = value;
    }


    @Override
    public String toString() {
        String stringRep = super.getName() + "=";

        for(String s: value) {
            stringRep += "\"" + s + "\",";
        }

        //trim last comma off
        return stringRep.substring(0, stringRep.length() - 1);
    }

    @Override
    public String toStringNoName() {
        String stringRep = "";

        for(String s: value) {
            stringRep += "\"" + s + "\",";
        }

        //trim last comma off
        return stringRep.substring(0, stringRep.length() - 1);
    }

}
