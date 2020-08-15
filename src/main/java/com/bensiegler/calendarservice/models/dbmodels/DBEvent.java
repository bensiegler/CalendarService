package com.bensiegler.calendarservice.models.dbmodels;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data

@Entity
@Table(name = "events")
public class DBEvent {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    private String name;

    @Column(name = "calendar_id")
    private UUID calendarId;


    public DBEvent(String name, UUID calendarId) {
        this.name = name;
        this.calendarId = calendarId;
    }
}
