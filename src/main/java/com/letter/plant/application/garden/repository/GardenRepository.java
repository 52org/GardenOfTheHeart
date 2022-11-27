package com.letter.plant.application.garden.repository;

import com.letter.plant.application.garden.domain.Garden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GardenRepository extends JpaRepository<Garden, Long> {

    Optional<Garden> findByUuid(String uuid);

}
