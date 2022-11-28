package com.letter.plant.application.garden.domain;

import com.letter.plant.core.jpa.TimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Plant extends TimeEntity {

    private Long letterId;

    private String plantName;

    private int wateringCount;

    @ManyToOne(fetch = FetchType.LAZY)
    Garden garden;

    @Builder
    public Plant(Long letterId, String plantName, int wateringCount, Garden garden) {

        this.letterId = letterId;
        this.plantName = plantName;
        this.wateringCount = wateringCount;
        this.garden = garden;

    }

    public void increaseCount() {

        wateringCount++;

    }

    public boolean isToday() {

        return wateringCount > 0 && getUpdatedAt().toLocalDate().isEqual(LocalDate.now());

    }

}
