package com._52org.gardenoftheheart.application.plant.service;

import com._52org.gardenoftheheart.application.plant.domain.Plant;
import com._52org.gardenoftheheart.application.plant.dto.PlantRequestDTO;
import com._52org.gardenoftheheart.application.plant.dto.PlantResponseDTO;
import com._52org.gardenoftheheart.application.plant.error.PlantErrorCode;
import com._52org.gardenoftheheart.application.plant.error.PlantException;
import com._52org.gardenoftheheart.application.plant.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlantService {

    private final PlantRepository plantRepository;

    @Transactional
    public PlantResponseDTO addPlant(final PlantRequestDTO plantRequestDTO) {

        plantRepository.findByPlantName(plantRequestDTO.getPlantName())
                .ifPresent(e -> {
                    throw new PlantException(PlantErrorCode.DUPLICATED_PLANTNAME);
                });

        final Plant plant = plantRepository.save(plantRequestDTO.toEntity());

        return PlantResponseDTO.toDTO(plant);

    }

    public List<PlantResponseDTO> getPlantList() {

        final List<Plant> plantList = plantRepository.findAll();

        return plantList.stream()
                .map(PlantResponseDTO::toDTO)
                .collect(Collectors.toList());

    }

    public PlantResponseDTO getPlant(final String plantName) {

        final Plant plant = plantRepository.findByPlantName(plantName)
                .orElseThrow(() -> new PlantException(PlantErrorCode.NON_EXISTENT_PLANT));

        return PlantResponseDTO.toDTO(plant);

    }

}
