package com.bensiegler.calendarservice.models.calstandard.parameters.stringlist;

import java.util.ArrayList;

public class DelegatedTo extends StringListParameter {

    public DelegatedTo() {
        super("DELEGATED-TO");
    }

    public DelegatedTo(ArrayList<String> values) {
        super("DELEGATED-TO", values);
    }
}
