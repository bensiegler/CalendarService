package com.bensiegler.calendarservice.models.dbmodel;

import javax.persistence.*;

@Entity
@Table(name = "events")
public class DBEvent {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @Column(name = "calendar_id")
    private Long calendarId;

    public DBEvent() {
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

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }
}
