package com.bensiegler.calendarservice.models.dbmodels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
@Data
@NoArgsConstructor


@Entity
@Table(name = "properties")
public class DBProperty {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull
    @Column(name = "calendar_id")
    private String calendarId;

    @NotNull
    @Column(name = "calendar_object_id")
    private String calendarObjectId;

    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @Size(max = 1000)
    private String content;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = DBParameter.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "calendar_object_id")
    private Collection<DBParameter> parameters = new ArrayList<>();

}
