package com.letter.plant.application.letter.api;

import com.letter.plant.application.letter.dto.LetterDetailDto;
import com.letter.plant.application.letter.dto.LetterDto;
import com.letter.plant.application.letter.dto.LetterWrapperDto;
import com.letter.plant.application.letter.service.LetterService;
import com.letter.plant.core.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LetterController {

    private final LetterService letterService;

    @PostMapping("/letter/send")
    private ApiResponse letterSend(@RequestBody LetterDto letterDto) {
        try {
            letterService.makeLetter(letterDto);
        } catch (Exception e) {
            return ApiResponse.fail("fail");
        }

        return ApiResponse.noContent();
    }

    @GetMapping("/letter/detail/{letterId}")
    private ApiResponse letterDetail(@PathVariable String letterId) {
        LetterDetailDto letter = letterService.getLetter(letterId);

        return ApiResponse.success(letter);
    }

    @GetMapping("/letter/list/{uuid}")
    private ApiResponse letterList(@PathVariable String uuid) {
        List<LetterWrapperDto> letters = letterService.getLetterList(uuid);

        return ApiResponse.success(letters);
    }

}
