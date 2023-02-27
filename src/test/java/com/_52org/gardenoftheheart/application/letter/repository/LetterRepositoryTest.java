package com._52org.gardenoftheheart.application.letter.repository;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import com._52org.gardenoftheheart.application.garden.repository.GardenRepository;
import com._52org.gardenoftheheart.application.letter.domain.Keyword;
import com._52org.gardenoftheheart.application.letter.domain.Letter;
import com._52org.gardenoftheheart.application.plant.domain.Plant;
import com._52org.gardenoftheheart.application.plant.repository.PlantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LetterRepositoryTest {

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Test
    public void 편지등록() {

        // given
        final Letter letter = Letter.builder()
                .author("범키")
                .message("행복하세요 ~~~ !!!!!!!")
                .build();

        final Keyword keyword = Keyword.builder()
                .letter(letter)
                .keyword("키워드")
                .build();

        // when
        final Letter letterResult = letterRepository.save(letter);
        final Keyword keywordResult = keywordRepository.save(keyword);

        // then
        assertThat(letterResult.getId()).isNotNull();
        assertThat(letterResult.getAuthor()).isEqualTo("범키");
        assertThat(letterResult.getMessage()).isEqualTo("행복하세요 ~~~ !!!!!!!");

        assertThat(keywordResult.getLetter()).isEqualTo(letter);
        assertThat(keywordResult.getKeyword()).isEqualTo("키워드");

    }

    @Test
    public void 편지조회() {

        // given
        final Letter letter = Letter.builder()
                .author("범키")
                .message("행복하세요 ~~~ !!!!!!!")
                .wateringCount(0)
                .build();

        final Keyword keyword1 = Keyword.builder()
                .letter(letter)
                .keyword("키워드1")
                .build();

        final Keyword keyword2 = Keyword.builder()
                .letter(letter)
                .keyword("키워드2")
                .build();

        // when
        final Letter letter1 = letterRepository.save(letter);
        keywordRepository.save(keyword1);
        keywordRepository.save(keyword2);

        final Letter letterResult = letterRepository.findById(letter1.getId()).orElse(null);
        final List<Keyword> keywordResult = keywordRepository.findByLetter(letter1);

        // then
        assertThat(letterResult).isNotNull();
        assertThat(letterResult.getId()).isNotNull();
        assertThat(letterResult.getAuthor()).isEqualTo("범키");
        assertThat(letterResult.getMessage()).isEqualTo("행복하세요 ~~~ !!!!!!!");

        assertThat(keywordResult).isNotNull();
        assertThat(keywordResult.get(0).getId()).isNotNull();
        assertThat(keywordResult.get(0).getLetter()).isEqualTo(letterResult);
        assertThat(keywordResult.get(0).getKeyword()).isEqualTo("키워드1");
        assertThat(keywordResult.get(1).getId()).isNotNull();
        assertThat(keywordResult.get(1).getLetter()).isEqualTo(letterResult);
        assertThat(keywordResult.get(1).getKeyword()).isEqualTo("키워드2");

    }

    @Test
    public void 편지리스트조회() {

        // given
        final Garden garden = Garden.builder()
                .gardenName("재이")
                .password("제이름은요")
                .uuid("123")
                .build();

        final Plant plant1 = Plant.builder()
                .plantName("해바라기")
                .growingPeriod(4)
                .description("사랑해바라기 !")
                .build();

        final Plant plant2 = Plant.builder()
                .plantName("바나나")
                .growingPeriod(1)
                .description("바나나를 먹으면 나한테 반ㅎ아니.")
                .build();

        final Letter letter1 = Letter.builder()
                .author("범키")
                .message("행복하세요 ~~~ !!!!!!!")
                .wateringCount(0)
                .plant(plant1)
                .garden(garden)
                .build();

        final Letter letter2 = Letter.builder()
                .author("범키")
                .message("답장해주세요 ~")
                .wateringCount(0)
                .plant(plant2)
                .garden(garden)
                .build();

        // when
        final Garden gardenResult = gardenRepository.save(garden);
        plantRepository.save(plant1);
        plantRepository.save(plant2);
        letterRepository.save(letter1);
        letterRepository.save(letter2);

        List<Letter> letterResult = letterRepository.findByGarden(gardenResult);

        // then
        assertThat(letterResult).isNotNull();
        assertThat(letterResult.get(0).getId()).isNotNull();
        assertThat(letterResult.get(0).getAuthor()).isEqualTo("범키");
        assertThat(letterResult.get(0).getMessage()).isEqualTo("행복하세요 ~~~ !!!!!!!");
        assertThat(letterResult.get(0).getWateringCount()).isEqualTo(0);
        assertThat(letterResult.get(0).getPlant()).isEqualTo(plant1);
        assertThat(letterResult.get(1).getId()).isNotNull();
        assertThat(letterResult.get(1).getAuthor()).isEqualTo("범키");
        assertThat(letterResult.get(1).getMessage()).isEqualTo("답장해주세요 ~");
        assertThat(letterResult.get(1).getWateringCount()).isEqualTo(0);
        assertThat(letterResult.get(1).getPlant()).isEqualTo(plant2);

    }

}
