package com.letter.plant.application.garden.service;

import com.letter.plant.application.garden.domain.Garden;
import com.letter.plant.application.garden.domain.GardenPlant;
import com.letter.plant.application.garden.domain.Plant;
import com.letter.plant.application.garden.dto.GardenDetailDto;
import com.letter.plant.application.garden.dto.GardenDto;
import com.letter.plant.application.garden.dto.PlantDto;
import com.letter.plant.application.garden.repository.GardenPlantRepository;
import com.letter.plant.application.garden.repository.GardenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GardenService {

    private final GardenRepository gardenRepository;

    private final GardenPlantRepository gardenPlantRepository;

    @Transactional
    public void createGarden(GardenDto gardenDto) {

        gardenRepository.save(gardenDto.toEntity());

    }

    @Transactional(readOnly = true)
    public GardenDetailDto getGarden(String uuid) {

        Garden garden = gardenRepository.findByUuid(uuid)
            .orElseThrow(RuntimeException::new);

        List<GardenPlant> gardenPlants = gardenPlantRepository.findByGarden(garden)
            .orElseThrow(RuntimeException::new);

        return GardenDetailDto.toDto(garden.getName(), gardenPlants);

    }

    @Transactional
    public void water(String letterId) {

        GardenPlant gardenPlant = gardenPlantRepository.findByLetterId(Long.parseLong(letterId))
            .orElseThrow(RuntimeException::new);

        gardenPlant.increaseCount();

    }
}
