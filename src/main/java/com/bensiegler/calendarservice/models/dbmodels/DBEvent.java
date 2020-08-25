package com.bensiegler.calendarservice.models.dbmodels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "events")
public class DBEvent {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "calendar_id")
    private String calendarId;

    public DBEvent(String name, String calendarId) {
        this.name = name;
        this.calendarId = calendarId;
    }
}
