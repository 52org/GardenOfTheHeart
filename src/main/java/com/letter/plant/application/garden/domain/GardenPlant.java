package com.letter.plant.application.garden.domain;

import com.letter.plant.core.jpa.TimeEntity;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
public class GardenPlant extends TimeEntity {

    private Long letterId;

    private int wateringCount;

    @ManyToOne(fetch = FetchType.LAZY)
    Garden garden;

    @ManyToOne(fetch = FetchType.LAZY)
    Plant plant;

    public void increaseCount() {
        wateringCount++;

        int growingPeriod = plant.getGrowingPeriod();
        if (wateringCount > growingPeriod)
            throw new RuntimeException();
    }

    public boolean isToday() {
        return getUpdatedAt().toLocalDate().isEqual(LocalDate.now());
    }

}
