package com.bensiegler.calendarservice.models.calstandard.parameters.string;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValueType extends StringParameter {

    public ValueType() {
        super("VALUE");
    }

    public ValueType(String value) {
        super("VALUE", value);
    }
}
