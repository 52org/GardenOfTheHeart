package com._52org.gardenoftheheart.application.seed.service;

import com._52org.gardenoftheheart.application.seed.domain.Seed;
import com._52org.gardenoftheheart.application.seed.dto.AddSeedRequestDTO;
import com._52org.gardenoftheheart.application.seed.dto.SeedResponseDTO;
import com._52org.gardenoftheheart.application.seed.repository.SeedRepository;
import com._52org.gardenoftheheart.exception.SeedErrorResult;
import com._52org.gardenoftheheart.exception.SeedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeedService {

    private final SeedRepository seedRepository;

    @Transactional
    public SeedResponseDTO addSeed(final AddSeedRequestDTO addSeedRequestDTO) {

        seedRepository.findByPlantName(addSeedRequestDTO.getPlantName())
                .ifPresent(e -> {
                    throw new SeedException(SeedErrorResult.DUPLICATED_SEED_REGISTER);
                });

        final Seed seed = seedRepository.save(addSeedRequestDTO.toEntity());

        return SeedResponseDTO.toDTO(seed);

    }

    public List<SeedResponseDTO> getSeedList() {

        final List<Seed> seedList = seedRepository.findAll();

        return seedList.stream()
                .map(SeedResponseDTO::toDTO)
                .collect(Collectors.toList());

    }

    public SeedResponseDTO getSeed(final String plantName) {

        final Seed seed = seedRepository.findByPlantName(plantName)
                .orElseThrow(() -> new SeedException(SeedErrorResult.SEED_NOT_FOUND));

        return SeedResponseDTO.toDTO(seed);

    }

}
