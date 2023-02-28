package com._52org.gardenoftheheart.application.plant.domain;

import com._52org.gardenoftheheart.core.jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plant extends BaseEntity {

    @Column(nullable = false)
    private String plantName;

    @Column(nullable = false)
    private Integer growingPeriod;

    @Lob
    @Column(nullable = false)
    private String description;

    @Builder
    public Plant(String plantName, Integer growingPeriod, String description) {
        this.plantName = plantName;
        this.growingPeriod = growingPeriod;
        this.description = description;
    }

}
