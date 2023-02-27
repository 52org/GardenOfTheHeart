package com._52org.gardenoftheheart.application.garden.repository;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GardenRepository extends JpaRepository<Garden, Long> {

    Optional<Garden> findByGardenName(final String gardenName);

    Optional<Garden> findByUuid(final String uuid);

}
