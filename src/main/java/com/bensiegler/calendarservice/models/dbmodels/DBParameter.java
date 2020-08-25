package com.bensiegler.calendarservice.models.dbmodels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "parameters")
public class DBParameter {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull
    @Column(name = "calendar_id")
    private String calendarId;
    @NotNull
    private String eventId;
    @NotNull
    private String propertyId;

    @NotNull
    @Size(max = 200)
    private String name;
    @NotNull
    @Size(max = 1000)
    private String content;

}