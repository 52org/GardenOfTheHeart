package com._52org.gardenoftheheart.application.seed.service;

import com._52org.gardenoftheheart.application.seed.domain.Seed;
import com._52org.gardenoftheheart.application.seed.dto.AddSeedRequestDTO;
import com._52org.gardenoftheheart.application.seed.dto.SeedResponseDTO;
import com._52org.gardenoftheheart.application.seed.error.SeedErrorCode;
import com._52org.gardenoftheheart.application.seed.error.SeedException;
import com._52org.gardenoftheheart.application.seed.repository.SeedRepository;
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
                    throw new SeedException(SeedErrorCode.DUPLICATED_PLANTNAME);
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
                .orElseThrow(() -> new SeedException(SeedErrorCode.NOT_EXIST_SEED));

        return SeedResponseDTO.toDTO(seed);

    }

}
