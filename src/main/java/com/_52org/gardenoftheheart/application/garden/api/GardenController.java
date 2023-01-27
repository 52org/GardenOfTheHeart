package com._52org.gardenoftheheart.application.garden.api;

import com._52org.gardenoftheheart.application.garden.dto.CreateGardenRequestDTO;
import com._52org.gardenoftheheart.application.garden.dto.GardenResponseDTO;
import com._52org.gardenoftheheart.application.garden.service.GardenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/garden")
public class GardenController {

    private final GardenService gardenService;

    @PostMapping
    public ResponseEntity<GardenResponseDTO> createGarden(@RequestBody @Valid final CreateGardenRequestDTO createGardenRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(gardenService.createGarden(createGardenRequestDTO));

    }

}
