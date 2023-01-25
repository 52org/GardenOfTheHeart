package com._52org.gardenoftheheart.application.seed.repository;

import com._52org.gardenoftheheart.application.seed.domain.Seed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeedRepository extends JpaRepository<Seed, Long> {

    Optional<Seed> findByPlantName(final String plantName);

}
