package com.letter.plant.application.garden.repository;

import com.letter.plant.application.garden.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
