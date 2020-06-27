package com.bensiegler.calendarservice.models.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "calendars")
public class DBCalendar {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @Size(max = 50)
    private String color;

    @NotNull
    private Long ownerId;

    @NotNull
    private String calScale;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = DBEvent.class)
    @JoinColumn(name = "calendar_id")
    private Collection<DBEvent> DBEvents = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = DBParameter.class)
    @JoinColumn(name = "calendar_id")
    private Collection<DBParameter> DBParameters = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = DBProperty.class)
    @JoinColumn(name = "calendar_id")
    private Collection<DBProperty> eventProperties = new ArrayList<>();

    public DBCalendar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getCalScale() {
        return calScale;
    }

    public void setCalScale(String calScale) {
        this.calScale = calScale;
    }

    public Collection<DBEvent> getDBEvents() {
        return DBEvents;
    }

    public void setDBEvents(Collection<DBEvent> DBEvents) {
        this.DBEvents = DBEvents;
    }

    public Collection<DBParameter> getDBParameters() {
        return DBParameters;
    }

    public void setDBParameters(Collection<DBParameter> DBParameters) {
        this.DBParameters = DBParameters;
    }

    public Collection<DBProperty> getEventProperties() {
        return eventProperties;
    }

    public void setEventProperties(Collection<DBProperty> eventProperties) {
        this.eventProperties = eventProperties;
    }
}
