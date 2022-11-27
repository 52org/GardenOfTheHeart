package com.letter.plant.application.garden.repository;

import com.letter.plant.application.garden.domain.Garden;
import com.letter.plant.application.garden.domain.GardenPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GardenPlantRepository extends JpaRepository<GardenPlant, Long> {

    Optional<List<GardenPlant>> findByGarden(Garden garden);

    Optional<GardenPlant> findByLetterId(Long letterId);

}
