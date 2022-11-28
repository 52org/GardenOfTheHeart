package com.letter.plant.application.garden.repository;

import com.letter.plant.application.garden.domain.Garden;
import com.letter.plant.application.garden.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    Optional<List<Plant>> findByGarden(Garden garden);

    Optional<Plant> findByLetterId(Long letterId);

}
