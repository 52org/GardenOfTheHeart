package com.letter.plant.application.garden.service;

import com.letter.plant.application.garden.domain.Garden;
import com.letter.plant.application.garden.domain.GardenPlant;
import com.letter.plant.application.garden.dto.GardenDetailDto;
import com.letter.plant.application.garden.dto.GardenDto;
import com.letter.plant.application.garden.dto.PlantDetailDto;
import com.letter.plant.application.garden.repository.GardenPlantRepository;
import com.letter.plant.application.garden.repository.GardenRepository;
import com.letter.plant.application.letter.domain.Letter;
import com.letter.plant.application.letter.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GardenService {

    private final GardenRepository gardenRepository;

    private final GardenPlantRepository gardenPlantRepository;

    private final LetterRepository letterRepository;

    @Transactional
    public boolean createGarden(GardenDto gardenDto) {

        if (gardenRepository.findByUuid(gardenDto.getUuid()).isPresent()) return false;

        gardenRepository.save(gardenDto.toEntity());

        return true;

    }

    @Transactional(readOnly = true)
    public GardenDetailDto getGarden(String uuid) {

        Garden garden = gardenRepository.findByUuid(uuid)
                .orElseThrow(RuntimeException::new);

        List<GardenPlant> gardenPlants = gardenPlantRepository.findByGarden(garden)
                .orElseThrow(RuntimeException::new);

        return GardenDetailDto.toDto(garden.getName(), gardenPlants);

    }

    @Transactional(readOnly = true)
    public PlantDetailDto getPlantDetail(Long letterId) {

        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(RuntimeException::new);

        GardenPlant gardenPlant = gardenPlantRepository.findByLetterId(letterId)
                .orElseThrow(RuntimeException::new);

        return PlantDetailDto.toDto(letter.getPlantName(), gardenPlant.getPlant().getGrowingPeriod(), gardenPlant.getWateringCount(), letter.getKeywords());

    }

    @Transactional
    public void water(Long letterId) {

        GardenPlant gardenPlant = gardenPlantRepository.findByLetterId(letterId)
                .orElseThrow(RuntimeException::new);

        gardenPlant.increaseCount();

        gardenPlantRepository.save(gardenPlant);

    }

}
