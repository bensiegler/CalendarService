package com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.LastModified;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZIdentifierProperty;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZUrl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

@Entity
@Table(name = "timezones")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TimeZone extends CalendarObject {

    @Id
    private String tzid;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @NotNull
    private Calendar lastModified;

    @Size(max = 200)
    private String tzUrl;

    @OneToMany(targetEntity = StandardOrDaylight.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "tzid")
    private Collection<StandardOrDaylight> standardOrDaylights;

    public TimeZone() {
        lastModified = Calendar.getInstance();
    }

    public String getTzid() {
        return tzid;
    }

    public void setTzid(String tzid) {
        this.tzid = tzid;
    }

    public Calendar getLastModified() {
        return lastModified;
    }

    public void setLastModified(Calendar lastModified) {
        this.lastModified = lastModified;
    }

    public String getTzUrl() {
        return tzUrl;
    }

    public void setTzUrl(String tzUrl) {
        this.tzUrl = tzUrl;
    }

    public Collection<StandardOrDaylight> getStandardOrDaylights() {
        return standardOrDaylights;
    }

    public void setStandardOrDaylights(ArrayList<StandardOrDaylight> standardOrDaylights) {
        this.standardOrDaylights = standardOrDaylights;
    }

    @Override
    public String retrieveCalStream() throws PropertyException, CalObjectException {
        validate();
        StringBuilder lines = new StringBuilder();
        lines.append("BEGIN:VTIMEZONE").append("\n");

        TZIdentifierProperty id = new TZIdentifierProperty();
        id.setContentUsingString(tzid);
        lines.append(Property.toCalStream(id)).append("\n");

        if(null != lastModified) {
            LastModified lm  = new LastModified(lastModified.getTimeInMillis());
            lines.append(Property.toCalStream(lm)).append("\n");
        }

        if(null != tzUrl) {
            TZUrl betterTZUrl = new TZUrl(tzUrl);
            lines.append(Property.toCalStream(betterTZUrl)).append("\n");
        }

        StandardOrDaylight[] standardOrDaylightArray = standardOrDaylights.toArray(new StandardOrDaylight[0]);
        for(StandardOrDaylight sd: standardOrDaylightArray) {
            lines.append(sd.retrieveCalStream());
        }

        lines.append("END:VTIMEZONE").append("\n");

        return lines.toString();
    }

    @Override
    public void validate() throws CalObjectException {
        if(null == tzid) {
            throw new CalObjectException("tzid cannot be null");
        }

        if(standardOrDaylights.size() < 1) {
            throw new CalObjectException("You are required to have at least one TimeZone Standard");
        }
    }
}
