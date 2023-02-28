package com._52org.gardenoftheheart.application.plant.api;

import com._52org.gardenoftheheart.application.plant.dto.PlantRequestDTO;
import com._52org.gardenoftheheart.application.plant.dto.PlantResponseDTO;
import com._52org.gardenoftheheart.application.plant.service.PlantService;
import com._52org.gardenoftheheart.core.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plant")
public class PlantController {

    private final PlantService plantService;

    @PostMapping
    public ResponseEntity<ApiResponse> addPlant(@RequestBody @Valid final PlantRequestDTO plantRequestDTO) {

        PlantResponseDTO plantResponseDTO = plantService.addPlant(plantRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(plantResponseDTO));

    }

    @GetMapping
    public ResponseEntity<ApiResponse> getPlantList() {

        return ResponseEntity.ok(ApiResponse.success(plantService.getPlantList()));

    }

    @GetMapping(value = "/{plantName}")
    public ResponseEntity<ApiResponse> getPlant(@PathVariable final String plantName) {

        return ResponseEntity.ok(ApiResponse.success(plantService.getPlant(plantName)));

    }

}
