package com._52org.gardenoftheheart;

import com._52org.gardenoftheheart.application.seed.domain.Seed;
import com._52org.gardenoftheheart.application.seed.dto.AddSeedRequestDTO;
import com._52org.gardenoftheheart.application.seed.repository.SeedRepository;
import com._52org.gardenoftheheart.exception.SeedErrorResult;
import com._52org.gardenoftheheart.exception.SeedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GardenOfTheHeartApplicationTests {

    @Autowired
    private SeedRepository seedRepository;
    @Test
    void contextLoads() {
        AddSeedRequestDTO addSeedRequestDTO = new AddSeedRequestDTO("해바라기", 4, "사랑해바라기 !");
        final Seed result = seedRepository.findByPlantName(addSeedRequestDTO.getPlantName()).orElse(null);

        if (result != null) {
            throw new SeedException(SeedErrorResult.DUPLICATED_SEED_REGISTER);
        };

        final Seed seed = Seed.builder()
                .plantName(addSeedRequestDTO.getPlantName())
                .growingPeriod(addSeedRequestDTO.getGrowingPeriod())
                .description(addSeedRequestDTO.getDescription())
                .build();
        System.out.println(seed);
        System.out.println(seedRepository.save(seed));
    }

}
