package com.bensiegler.calendarservice.models.dbmodels;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data

@Entity
@Table(name = "events")
public class DBEvent {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    private String name;

    @Column(name = "calendar_id")
    private String calendarId;


    public DBEvent(String name, String calendarId) {
        this.name = name;
        this.calendarId = calendarId;
    }
}
