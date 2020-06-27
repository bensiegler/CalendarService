package com.bensiegler.calendarservice.models.calstandard.parameters.stringlist;

import java.util.ArrayList;

public class Member extends StringListParameter {

    public Member() {
        super("MEMBER");
    }

    public Member(ArrayList<String> values) {
        super("MEMBER", values);
    }
}
