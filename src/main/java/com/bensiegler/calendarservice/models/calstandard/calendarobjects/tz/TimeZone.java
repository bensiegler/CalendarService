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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

@Entity
@Table(name = "timezones")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TimeZone extends CalendarObject {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    private String idCountry;

    @NotNull
    private String idCity;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @NotNull
    private Calendar lastModified;

    @Size(max = 200)
    private String tzUrl;


    @OneToMany(targetEntity = StandardOrDaylight.class)
    @JoinColumn(name = "timezone_id")
    private Collection<StandardOrDaylight> standardOrDaylights;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
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
    public ArrayList<String> getCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException {
        validate();
        ArrayList<String> lines = new ArrayList<>();
        lines.add("BEGIN:VTIMEZONE");

        TZIdentifierProperty id = new TZIdentifierProperty(idCountry, idCity);
        lines.add(Property.toCalStream(id));


        if(null != lastModified) {
            LastModified lm  = new LastModified(lastModified.getTimeInMillis());
            lines.add(Property.toCalStream(lm));
        }

        if(null != tzUrl) {
            TZUrl betterTZUrl = new TZUrl(tzUrl);
            lines.add(Property.toCalStream(betterTZUrl));
        }

        StandardOrDaylight[] standardOrDaylightArray = standardOrDaylights.toArray(new StandardOrDaylight[0]);
        for(StandardOrDaylight sd: standardOrDaylightArray) {
            lines.addAll(sd.getCalStream());
        }

        lines.add("END:VTIMEZONE");

        return lines;
    }

    @Override
    public void validate() throws CalObjectException {
        if(null == idCountry) {
            throw new CalObjectException("country cannot be null");
        }

        if(null == idCity) {
            throw new CalObjectException("city cannot be null");
        }

        if(standardOrDaylights.size() < 1) {
            throw new CalObjectException("You are required to have at least one TimeZone Standard");
        }

    }
}
