package com.bensiegler.calendarservice.models.dbmodels;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Data

@Entity
@Table(name = "calendars")
public class DBCalendar {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Size(max = 200)
    private String name;

    @Size(max = 50)
    private String color;

    private String ownerId;

    private String calScale;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = DBEvent.class)
    @JoinColumn(name = "calendar_id")
    private Collection<DBEvent> DBEvents = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = DBAlarm.class)
    @JoinColumn(name = "calendar_id")
    private Collection<DBAlarm> DBAlarms = new ArrayList<>();

}
