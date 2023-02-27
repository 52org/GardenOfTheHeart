package com._52org.gardenoftheheart.application.letter.service;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import com._52org.gardenoftheheart.application.garden.repository.GardenRepository;
import com._52org.gardenoftheheart.application.letter.domain.Keyword;
import com._52org.gardenoftheheart.application.letter.domain.Letter;
import com._52org.gardenoftheheart.application.letter.dto.LetterDetailResponseDTO;
import com._52org.gardenoftheheart.application.letter.dto.LetterListResponseDTO;
import com._52org.gardenoftheheart.application.letter.dto.LetterRequestDTO;
import com._52org.gardenoftheheart.application.letter.dto.LetterResponseDTO;
import com._52org.gardenoftheheart.application.letter.repository.KeywordRepository;
import com._52org.gardenoftheheart.application.letter.repository.LetterRepository;
import com._52org.gardenoftheheart.application.plant.domain.Plant;
import com._52org.gardenoftheheart.application.plant.repository.PlantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LetterServiceTest {

    @InjectMocks
    private LetterService target;

    @Mock
    private LetterRepository letterRepository;

    @Mock
    private GardenRepository gardenRepository;

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private KeywordRepository keywordRepository;

    private final String author = "범키";
    private final String message = "행복하세요 ~~~ !!!!!!!";
    private final Integer wateringCount = 0;
    private final List<String> keywords = new ArrayList<>(Arrays.asList("키워드1", "키워드2"));
    private final LetterRequestDTO letterRequestDTO = new LetterRequestDTO("123", "해바라기", author, message, keywords);

    private final Garden garden = Garden.builder()
            .gardenName("재이")
            .password("제이름은요")
            .uuid("123")
            .build();

    private final Plant plant1 = Plant.builder()
            .plantName("해바라기")
            .growingPeriod(4)
            .description("사랑해바라기 !")
            .build();

    private final Plant plant2 = Plant.builder()
            .plantName("바나나")
            .growingPeriod(1)
            .description("바나나를 먹으면 나한테 반ㅎ아니.")
            .build();

    @Test
    public void 편지전송성공() {

        // given
        doReturn(Optional.of(garden)).when(gardenRepository).findByUuid(garden.getUuid());
        doReturn(Optional.of(plant1)).when(plantRepository).findByPlantName(plant1.getPlantName());
        doReturn(letter()).when(letterRepository).save(any(Letter.class));
        doReturn(Optional.of(letter())).when(letterRepository).findById(1L);
//        doReturn(keyword1()).when(keywordRepository).save(any(Keyword.class));
//        doReturn(keyword2()).when(keywordRepository).save(any(Keyword.class));
        doReturn(List.of(keyword1(), keyword2())).when(keywordRepository).findByLetter(any(Letter.class));

        // when
        target.sendLetter(letterRequestDTO);
        final Letter letterResult = letterRepository.findById(1L).orElse(null);
        final List<Keyword> keywordResult = keywordRepository.findByLetter(letterResult);

        // then
        assertThat(letterResult).isNotNull();
        assertThat(letterResult.getAuthor()).isEqualTo(author);

        assertThat(keywordResult).isNotNull();
        assertThat(keywordResult.get(0).getKeyword()).isEqualTo("키워드1");

        // verify
        verify(gardenRepository, times(1)).findByUuid(garden.getUuid());
        verify(plantRepository, times(1)).findByPlantName(plant1.getPlantName());
        verify(letterRepository, times(1)).save(any(Letter.class));
        verify(letterRepository, times(1)).findById(1L);
        verify(keywordRepository, times(2)).save(any(Keyword.class));
        verify(keywordRepository, times(1)).findByLetter(any(Letter.class));

    }

    @Test
    public void 편지목록조회() {

        // given
        doReturn(Optional.of(garden)).when(gardenRepository).findByUuid(garden.getUuid());
        doReturn(List.of(
                letter(),
                letter(),
                letter()
        )).when(letterRepository).findByGarden(garden);

        // when
        final List<LetterListResponseDTO> result = target.getLetterList(garden.getUuid());

        // then
        assertThat(result.size()).isEqualTo(3);

    }

    @Test
    public void 편지조회() {

        // given
        doReturn(Optional.of(letter())).when(letterRepository).findById(letter().getId());
        doReturn(List.of(keyword1(), keyword2())).when(keywordRepository).findByLetter(any(Letter.class));

        // when
        final LetterResponseDTO letterResult = target.getLetter(letter().getId());

        // then
        assertThat(letterResult).isNotNull();
        assertThat(letterResult.getKeywords().size()).isEqualTo(2);

    }

    @Test
    public void 편지상세조회() {

        // given
        doReturn(Optional.of(letter())).when(letterRepository).findById(letter().getId());

        // when
        final LetterDetailResponseDTO letterResult = target.getLetterDetail(letter().getId());

        // then
        assertThat(letterResult).isNotNull();

    }

    private Letter letter() {

        return Letter.builder()
                .author(author)
                .message(message)
                .wateringCount(wateringCount)
                .plant(plant1)
                .garden(garden)
                .build();

    }

    private Keyword keyword1() {

        return Keyword.builder()
                .letter(letter())
                .keyword("키워드1")
                .build();

    }

    private Keyword keyword2() {

        return Keyword.builder()
                .letter(letter())
                .keyword("키워드2")
                .build();

    }

}
