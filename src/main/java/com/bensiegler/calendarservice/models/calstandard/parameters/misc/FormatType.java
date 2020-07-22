package com.bensiegler.calendarservice.models.calstandard.parameters.misc;

import com.bensiegler.calendarservice.models.calstandard.parameters.Parameter;

public class FormatType extends Parameter {
    private String type;
    private String subtype;

    public FormatType() {
        super("FMTTYPE");
    }

    public FormatType(String type, String subtype) {
        super("FMTTYPE");
        this.type = type;
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    @Override
    public String toString() {
        return super.getName() + "=" + type + "/" + subtype;
    }

    @Override
    public String toStringNoName() {
        return type + "/" + subtype;
    }


}
