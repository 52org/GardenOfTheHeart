package com._52org.gardenoftheheart.application.letter.controller;

import com._52org.gardenoftheheart.application.letter.dto.LetterRequestDTO;
import com._52org.gardenoftheheart.application.letter.service.LetterService;
import com._52org.gardenoftheheart.core.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letter")
public class LetterController {

    private final LetterService letterService;

    @PostMapping
    public ResponseEntity<ApiResponse> sendLetter(@RequestBody @Valid final LetterRequestDTO letterRequestDTO) {

        letterService.sendLetter(letterRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(null));

    }

    @GetMapping("/list/{gardenId}")
    public ResponseEntity<ApiResponse> getLetterList(@PathVariable final String gardenId) {

        return ResponseEntity.ok(ApiResponse.success(letterService.getLetterList(gardenId)));

    }

    @GetMapping("/{letterId}")
    public ResponseEntity<ApiResponse> getLetter(@PathVariable final Long letterId) {

        return ResponseEntity.ok(ApiResponse.success(letterService.getLetter(letterId)));

    }

    @GetMapping("/detail/{letterId}")
    public ResponseEntity<ApiResponse> getLetterDetail(@PathVariable final Long letterId) {

        return ResponseEntity.ok(ApiResponse.success(letterService.getLetterDetail(letterId)));

    }

}
