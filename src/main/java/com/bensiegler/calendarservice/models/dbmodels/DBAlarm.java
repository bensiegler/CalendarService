package com.bensiegler.calendarservice.models.dbmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "alarms")
public class DBAlarm {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, name = "calendar_id")
    private String calendarId;

    @Column(nullable = false, name = "event_id")
    private String eventId;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = DBProperty.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "calendar_object_id")
    private Collection<DBProperty> properties = new ArrayList<>();

}
