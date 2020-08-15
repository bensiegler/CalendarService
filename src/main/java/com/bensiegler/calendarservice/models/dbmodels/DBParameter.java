package com.bensiegler.calendarservice.models.dbmodels;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data

@Entity
@Table(name = "parameters")
public class DBParameter {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "calendar_id")
    private UUID calendarId;
    @NotNull
    private UUID eventId;
    @NotNull
    private UUID propertyId;

    @NotNull
    @Size(max = 200)
    private String name;
    @NotNull
    @Size(max = 1000)
    private String content;

}