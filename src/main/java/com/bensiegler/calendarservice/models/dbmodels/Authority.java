package com.bensiegler.calendarservice.models.dbmodels;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "calservicetest", name = "authorities")

@Data
public class Authority {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID calendarObjectId;

    @Column(nullable = true)
    private UUID actionTakenBy;

    @Column(nullable = false)
    private UUID authorityGrantedTo;

    @Column(nullable = false)
    private String powerGiven;

    public String getAuthorityString() {
        return powerGiven + "_" + calendarObjectId.toString() + "_" + authorityGrantedTo;
    }


}
