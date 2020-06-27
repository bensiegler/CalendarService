package com.bensiegler.calendarservice.models.calstandard.parameters.stringlist;

import java.util.ArrayList;

public class DelegatedFrom extends StringListParameter {

    public DelegatedFrom() {
        super("DELEGATED-FROM");
    }

    public DelegatedFrom(ArrayList<String> values) {
        super("DELEGATED-FROM", values);
    }
}
