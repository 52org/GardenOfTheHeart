package com._52org.gardenoftheheart.application.plant.repository;

import com._52org.gardenoftheheart.application.plant.domain.Plant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // JPA Repository 들에 대한 빈들을 등록하여 단위 테스트의 작성을 용이하게 함
public class PlantRepositoryTest {

    @Autowired
    private PlantRepository plantRepository;

    @Test
    public void 씨앗등록() {

        // given
        final Plant plant = Plant.builder()
                .plantName("해바라기")
                .growingPeriod(4)
                .description("사랑해바라기 !")
                .build();

        // when
        final Plant result = plantRepository.save(plant);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getPlantName()).isEqualTo("해바라기");
        assertThat(result.getGrowingPeriod()).isEqualTo(4);
        assertThat(result.getDescription()).isEqualTo("사랑해바라기 !");

    }

    @Test
    public void 씨앗조회() {

        // given
        final Plant plant = Plant.builder()
                .plantName("해바라기")
                .growingPeriod(4)
                .description("사랑해바라기 !")
                .build();

        // when
        plantRepository.save(plant);

        final Plant result = plantRepository.findByPlantName("해바라기").orElse(null);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getPlantName()).isEqualTo("해바라기");
        assertThat(result.getGrowingPeriod()).isEqualTo(4);
        assertThat(result.getDescription()).isEqualTo("사랑해바라기 !");

    }

    @Test
    public void 씨앗리스트조회() {

        // given
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

        // when
        plantRepository.save(plant1);
        plantRepository.save(plant2);

        final List<Plant> result = plantRepository.findAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result.get(0).getId()).isNotNull();
        assertThat(result.get(0).getPlantName()).isEqualTo("해바라기");
        assertThat(result.get(0).getGrowingPeriod()).isEqualTo(4);
        assertThat(result.get(0).getDescription()).isEqualTo("사랑해바라기 !");
        assertThat(result.get(1).getId()).isNotNull();
        assertThat(result.get(1).getPlantName()).isEqualTo("바나나");
        assertThat(result.get(1).getGrowingPeriod()).isEqualTo(1);
        assertThat(result.get(1).getDescription()).isEqualTo("바나나를 먹으면 나한테 반ㅎ아니.");

    }

}
