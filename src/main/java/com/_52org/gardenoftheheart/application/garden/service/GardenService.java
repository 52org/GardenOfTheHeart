package com._52org.gardenoftheheart.application.garden.service;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import com._52org.gardenoftheheart.application.garden.dto.CreateGardenRequestDTO;
import com._52org.gardenoftheheart.application.garden.dto.GardenResponseDTO;
import com._52org.gardenoftheheart.application.garden.error.GardenErrorCode;
import com._52org.gardenoftheheart.application.garden.error.GardenException;
import com._52org.gardenoftheheart.application.garden.repository.GardenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GardenService {

    private final GardenRepository gardenRepository;

    @Transactional
    public GardenResponseDTO createGarden(final CreateGardenRequestDTO createGardenRequestDTO) {

        gardenRepository.findByGardenName(createGardenRequestDTO.getGardenName())
                .ifPresent(e -> {
                    throw new GardenException(GardenErrorCode.DUPLICATED_GARDENNAME);
                });

        final Garden garden = gardenRepository.save(createGardenRequestDTO.toEntity());

        return GardenResponseDTO.toDTO(garden);

    }

}
