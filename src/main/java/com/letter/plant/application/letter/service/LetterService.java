package com.letter.plant.application.letter.service;

import com.letter.plant.application.garden.domain.Garden;
import com.letter.plant.application.garden.repository.GardenRepository;
import com.letter.plant.application.garden.service.GardenService;
import com.letter.plant.application.letter.domain.Letter;
import com.letter.plant.application.letter.dto.LetterDetailDto;
import com.letter.plant.application.letter.dto.LetterDto;
import com.letter.plant.application.letter.dto.LetterWrapperDto;
import com.letter.plant.application.letter.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class LetterService {

    private final LetterRepository letterRepository;
    private final GardenRepository gardenRepository;

    public void makeLetter(LetterDto letterDto) {
        Garden garden = gardenRepository.findById(letterDto.getGardenUuid())
            .orElseThrow(RuntimeException::new);
        Letter letter = letterDto.toEntity();
        letter.addKeywords(letterDto.getKeyWords());
        letter.addGarden(garden);

        letterRepository.save(letter);
    }


    @Transactional(readOnly = true)
    public LetterDetailDto getLetter(String letterId) {
        Letter letter = letterRepository.findById(Long.parseLong(letterId))
            .orElseThrow(RuntimeException::new);

        return LetterDetailDto.toDto(letter);
    }

    @Transactional(readOnly = true)
    public List<LetterWrapperDto> getLetterList(String uuid) {
        Garden garden = gardenRepository.findById(Long.parseLong(uuid))
            .orElseThrow(RuntimeException::new);

        List<Letter> letters = garden.getLetters();
        return LetterWrapperDto.toDto(letters);
    }
}
