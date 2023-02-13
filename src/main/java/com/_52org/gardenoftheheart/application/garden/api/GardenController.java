package com._52org.gardenoftheheart.application.garden.api;

import com._52org.gardenoftheheart.application.garden.dto.GardenLoginResponseDTO;
import com._52org.gardenoftheheart.application.garden.dto.GardenRequestDTO;
import com._52org.gardenoftheheart.application.garden.dto.GardenSignUpResponseDTO;
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

    @PostMapping("/join")
    public ResponseEntity<GardenSignUpResponseDTO> createGarden(@RequestBody @Valid final GardenRequestDTO gardenRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(gardenService.createGarden(gardenRequestDTO));

    }

    @PostMapping("/login")
    public ResponseEntity<GardenLoginResponseDTO> login(@RequestBody @Valid final GardenRequestDTO gardenRequestDTO) {

        return ResponseEntity.ok(gardenService.login(gardenRequestDTO));

    }

}
