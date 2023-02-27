package com._52org.gardenoftheheart.application.letter.domain;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import com._52org.gardenoftheheart.application.plant.domain.Plant;
import com._52org.gardenoftheheart.core.jpa.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Letter extends BaseEntity {

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    @Lob
    private String message;

    @Column(nullable = false)
    private Integer wateringCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id")
    private Garden garden;

    @Builder
    public Letter(String author, String message, Integer wateringCount, Plant plant, Garden garden) {
        this.author = author;
        this.message = message;
        this.wateringCount = wateringCount;
        this.plant = plant;
        this.garden = garden;
    }

}
