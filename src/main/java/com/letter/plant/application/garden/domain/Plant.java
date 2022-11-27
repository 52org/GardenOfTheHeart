package com.letter.plant.application.garden.domain;

import com.letter.plant.core.jpa.TimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class Plant extends TimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Garden garden;

    private String name;

    private int growingPeriod;

    @Lob
    private String description;

}
