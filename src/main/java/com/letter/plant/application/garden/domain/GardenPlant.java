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
public class GardenPlant extends TimeEntity {

    private Long letterId;

    private int wateringCount;

    @ManyToOne(fetch = FetchType.LAZY)
    Garden garden;

    @ManyToOne(fetch = FetchType.LAZY)
    Plant plant;

    @Builder
    public GardenPlant(Long letterId, int wateringCount, Garden garden, Plant plant) {

        this.letterId = letterId;
        this.wateringCount = wateringCount;
        this.garden = garden;
        this.plant = plant;

    }

    public void increaseCount() {

        wateringCount++;

        int growingPeriod = plant.getGrowingPeriod();

        if (wateringCount > growingPeriod) throw new RuntimeException();

    }

    public boolean isToday() {

        return wateringCount > 0 && getUpdatedAt().toLocalDate().isEqual(LocalDate.now());

    }

}
