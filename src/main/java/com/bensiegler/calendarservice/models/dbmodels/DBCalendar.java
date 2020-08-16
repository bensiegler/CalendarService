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

    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @Size(max = 50)
    private String color;

    @NotNull
    private String ownerId;

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

}
