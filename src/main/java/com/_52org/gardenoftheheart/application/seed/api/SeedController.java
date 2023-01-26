package com._52org.gardenoftheheart.application.seed.api;

import com._52org.gardenoftheheart.application.seed.dto.AddSeedRequestDTO;
import com._52org.gardenoftheheart.application.seed.dto.SeedResponseDTO;
import com._52org.gardenoftheheart.application.seed.service.SeedService;
import com._52org.gardenoftheheart.core.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seed")
public class SeedController {

    private final SeedService seedService;

    @PostMapping
    public ResponseEntity<ApiResponse> addSeed(@RequestBody @Valid final AddSeedRequestDTO addSeedRequestDTO) {

        SeedResponseDTO seedResponseDTO = seedService.addSeed(addSeedRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(seedResponseDTO));

    }

    @GetMapping
    public ResponseEntity<ApiResponse> getSeedList() {

        return ResponseEntity.ok(ApiResponse.success(seedService.getSeedList()));

    }

    @GetMapping(value = "/{plantName}")
    public ResponseEntity<ApiResponse> getSeed(@PathVariable final String plantName) {

        return ResponseEntity.ok(ApiResponse.success(seedService.getSeed(plantName)));

    }

}
