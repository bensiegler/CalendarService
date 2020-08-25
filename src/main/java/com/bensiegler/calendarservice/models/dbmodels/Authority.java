package com.bensiegler.calendarservice.models.dbmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "calservicetest", name = "authorities")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String calendarObjectId;

    @Column(nullable = false)
    private String actionTakenBy;

    @Column(nullable = false)
    private String authorityGrantedTo;

    @Column(nullable = false)
    private String powerGiven;

    public String getAuthorityString() {
        return powerGiven + "_" + calendarObjectId + "_" + authorityGrantedTo;
    }

    public void formatUUIDs() {
        if(null != id) {
            id = id.trim();
        }

        if(null != calendarObjectId) {
            calendarObjectId = calendarObjectId.trim();
        }

        if(null != actionTakenBy) {
            actionTakenBy = actionTakenBy.trim();
        }

        if(null != authorityGrantedTo) {
            authorityGrantedTo = authorityGrantedTo.trim();        }

    }

}
