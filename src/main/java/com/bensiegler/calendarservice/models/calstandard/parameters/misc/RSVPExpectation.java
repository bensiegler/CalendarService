package com.bensiegler.calendarservice.models.calstandard.parameters.misc;

import com.bensiegler.calendarservice.models.calstandard.parameters.Parameter;

public class RSVPExpectation extends Parameter {
    private boolean RSVP;

    public RSVPExpectation() {
        super("RSVP");
    }

    public RSVPExpectation(boolean RSVP) {
        super("RSVP");
        this.RSVP = RSVP;
    }

    public boolean getRSVP() {
        return RSVP;
    }

    public void setRSVP(boolean RSVP) {
        this.RSVP = RSVP;
    }

    @Override
    public String toString() {
        String rep = super.getName() + "=";

        if(RSVP) {
            return rep + "TRUE";
        }else{
            return rep + "FALSE";
        }
    }

    @Override
    public String toStringNoName() {
        if(RSVP) {
            return "TRUE";
        }else{
            return "FALSE";
        }
    }
}
