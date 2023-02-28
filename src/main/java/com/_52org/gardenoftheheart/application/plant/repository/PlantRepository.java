package com._52org.gardenoftheheart.application.plant.repository;

import com._52org.gardenoftheheart.application.plant.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    Optional<Plant> findByPlantName(final String plantName);

}
