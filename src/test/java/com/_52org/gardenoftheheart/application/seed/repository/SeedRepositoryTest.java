package com._52org.gardenoftheheart.application.seed.repository;

import com._52org.gardenoftheheart.application.seed.domain.Seed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // JPA Repository 들에 대한 빈들을 등록하여 단위 테스트의 작성을 용이하게 함
public class SeedRepositoryTest {

    @Autowired
    private SeedRepository seedRepository;

    @Test
    public void 씨앗등록() {

        // given
        final Seed seed = Seed.builder()
                .plantName("해바라기")
                .growingPeriod(4)
                .description("사랑해바라기 !")
                .build();

        // when
        final Seed result = seedRepository.save(seed);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getPlantName()).isEqualTo("해바라기");
        assertThat(result.getGrowingPeriod()).isEqualTo(4);
        assertThat(result.getDescription()).isEqualTo("사랑해바라기 !");

    }

    @Test
    public void 씨앗조회() {

        // given
        final Seed seed = Seed.builder()
                .plantName("해바라기")
                .growingPeriod(4)
                .description("사랑해바라기 !")
                .build();

        // when
        seedRepository.save(seed);

        final Seed result = seedRepository.findByPlantName("해바라기").orElse(null);

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
        final Seed seed1 = Seed.builder()
                .plantName("해바라기")
                .growingPeriod(4)
                .description("사랑해바라기 !")
                .build();
        final Seed seed2 = Seed.builder()
                .plantName("바나나")
                .growingPeriod(1)
                .description("바나나를 먹으면 나한테 반ㅎ아니.")
                .build();

        // when
        seedRepository.save(seed1);
        seedRepository.save(seed2);

        final List<Seed> result = seedRepository.findAll();

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
