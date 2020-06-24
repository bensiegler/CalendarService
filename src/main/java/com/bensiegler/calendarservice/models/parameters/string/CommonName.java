package com.bensiegler.calendarservice.models.parameters.string;

public class CommonName extends StringParameter {

    public CommonName() {
        super("CN");
    }

    public CommonName(String value) {
        super("CN", value);
    }
}
