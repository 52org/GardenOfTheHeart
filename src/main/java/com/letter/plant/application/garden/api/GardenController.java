package com.letter.plant.application.garden.api;

import com.letter.plant.application.garden.dto.GardenDetailDto;
import com.letter.plant.application.garden.dto.GardenDto;
import com.letter.plant.application.garden.dto.PlantDetailDto;
import com.letter.plant.application.garden.service.GardenService;
import com.letter.plant.core.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/garden")
public class GardenController {

    private final GardenService gardenService;

    @PostMapping
    public ApiResponse createGarden(@RequestBody GardenDto gardenDto) {

        gardenService.createGarden(gardenDto);

        return ApiResponse.noContent();

    }

    @GetMapping("/{gardenId}")
    public ApiResponse readGarden(@PathVariable String gardenId) {

        GardenDetailDto gardenDetailDto = gardenService.getGarden(gardenId);

        return ApiResponse.success(gardenDetailDto);

    }

    @GetMapping("/detail/{letterId}")
    public ApiResponse readPlantDetail(@PathVariable Long letterId) {

        PlantDetailDto plantDetailDto = gardenService.getPlantDetail(letterId);

        return ApiResponse.success(plantDetailDto);

    }

    @PutMapping("/water/{letterId}")
    public ApiResponse water(@PathVariable Long letterId) {

        gardenService.water(letterId);

        return ApiResponse.noContent();

    }

}
