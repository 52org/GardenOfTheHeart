package com._52org.gardenoftheheart.application.garden.repository;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GardenRepositoryTest {

    @Autowired
    private GardenRepository gardenRepository;

    @Test
    public void 텃밭생성() {

        // given
        final Garden garden = Garden.builder()
                .gardenName("재이")
                .password("제이름은요")
                .build();

        // when
        final Garden result = gardenRepository.save(garden);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getGardenName()).isEqualTo("재이");
        assertThat(result.getPassword()).isEqualTo("제이름은요");

    }

    @Test
    public void 텃밭존재여부확인() {

        // given
        final Garden garden = Garden.builder()
                .gardenName("재이")
                .password("제이름은요")
                .build();

        // when
        final Garden savedResult = gardenRepository.save(garden);
        final Garden findResult = gardenRepository.findByGardenName("재이").orElse(null);

        // then
        assertThat(findResult).isNotNull();
        assertThat(findResult.getGardenName()).isNotNull();
        assertThat(findResult).isEqualTo(savedResult);

    }

}
