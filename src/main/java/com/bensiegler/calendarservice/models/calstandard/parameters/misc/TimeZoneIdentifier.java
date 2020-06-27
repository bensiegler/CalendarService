package com.bensiegler.calendarservice.models.calstandard.parameters.misc;

import com.bensiegler.calendarservice.models.calstandard.parameters.Parameter;

public class TimeZoneIdentifier extends Parameter {
    private String continent;
    private String city;

    public TimeZoneIdentifier() {
        super("TZID");
    }

    public TimeZoneIdentifier(String continent, String city) {
        super("TZID");
        this.continent = continent;
        this.city = city;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "TZID=" + continent + "/" + city;
    }

    @Override
    public String toStringNoName() {
        return continent + "/" + city;
    }
}
