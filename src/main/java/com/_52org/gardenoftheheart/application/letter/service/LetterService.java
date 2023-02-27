package com._52org.gardenoftheheart.application.letter.service;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import com._52org.gardenoftheheart.application.garden.error.GardenErrorCode;
import com._52org.gardenoftheheart.application.garden.error.GardenException;
import com._52org.gardenoftheheart.application.garden.repository.GardenRepository;
import com._52org.gardenoftheheart.application.letter.domain.Keyword;
import com._52org.gardenoftheheart.application.letter.domain.Letter;
import com._52org.gardenoftheheart.application.letter.dto.LetterDetailResponseDTO;
import com._52org.gardenoftheheart.application.letter.dto.LetterListResponseDTO;
import com._52org.gardenoftheheart.application.letter.dto.LetterRequestDTO;
import com._52org.gardenoftheheart.application.letter.dto.LetterResponseDTO;
import com._52org.gardenoftheheart.application.letter.error.LetterErrorCode;
import com._52org.gardenoftheheart.application.letter.error.LetterException;
import com._52org.gardenoftheheart.application.letter.repository.KeywordRepository;
import com._52org.gardenoftheheart.application.letter.repository.LetterRepository;
import com._52org.gardenoftheheart.application.plant.domain.Plant;
import com._52org.gardenoftheheart.application.plant.error.PlantErrorCode;
import com._52org.gardenoftheheart.application.plant.error.PlantException;
import com._52org.gardenoftheheart.application.plant.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;

    private final GardenRepository gardenRepository;

    private final PlantRepository plantRepository;

    private final KeywordRepository keywordRepository;

    @Transactional
    public void sendLetter(final LetterRequestDTO letterRequestDTO) {

        final Garden garden = gardenRepository.findByUuid(letterRequestDTO.getGardenId())
                .orElseThrow(() -> new GardenException(GardenErrorCode.NON_EXISTENT_GARDEN));

        final Plant plant = plantRepository.findByPlantName(letterRequestDTO.getPlantName())
                .orElseThrow(() -> new PlantException(PlantErrorCode.NON_EXISTENT_PLANT));

        final Letter letter = Letter.builder()
                .author(letterRequestDTO.getAuthor())
                .message(letterRequestDTO.getMessage())
                .wateringCount(0)
                .plant(plant)
                .garden(garden)
                .build();

        letterRepository.save(letter);

        List<String> keywords = letterRequestDTO.getKeywords();

        for (String keyword : keywords) {
            keywordRepository.save(
                    Keyword.builder()
                            .letter(letter)
                            .keyword(keyword)
                            .build()
            );
        }

    }

    @Transactional
    public List<LetterListResponseDTO> getLetterList(final String gardenId) {

        final Garden garden = gardenRepository.findByUuid(gardenId)
                .orElseThrow(() -> new GardenException(GardenErrorCode.NON_EXISTENT_GARDEN));

        List<Letter> letterList = letterRepository.findByGarden(garden);

        return letterList.stream()
                .map(LetterListResponseDTO::toDTO)
                .collect(Collectors.toList());

    }

    @Transactional
    public LetterResponseDTO getLetter(Long letterId) {

        final Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterException(LetterErrorCode.NON_EXISTENT_LETTER));

        final List<String> keywordList =
                keywordRepository.findByLetter(letter)
                        .stream()
                        .map(Keyword::getKeyword)
                        .collect(Collectors.toList());

        return LetterResponseDTO.toDTO(letter, keywordList);

    }

    @Transactional
    public LetterDetailResponseDTO getLetterDetail(Long letterId) {

        final Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterException(LetterErrorCode.NON_EXISTENT_LETTER));

        return LetterDetailResponseDTO.toDTO(letter);

    }

}
