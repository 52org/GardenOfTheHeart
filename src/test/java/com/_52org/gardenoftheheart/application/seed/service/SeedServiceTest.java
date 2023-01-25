package com._52org.gardenoftheheart.application.seed.service;

import com._52org.gardenoftheheart.application.seed.domain.Seed;
import com._52org.gardenoftheheart.application.seed.dto.AddSeedRequestDTO;
import com._52org.gardenoftheheart.application.seed.dto.SeedResponseDTO;
import com._52org.gardenoftheheart.application.seed.repository.SeedRepository;
import com._52org.gardenoftheheart.exception.seed.SeedErrorResult;
import com._52org.gardenoftheheart.exception.seed.SeedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeedServiceTest {

    // 테스트 대상이므로 의존성이 주입되는 어노테이션 @InjectMocks
    @InjectMocks
    private SeedService target;

    // 의존성이 있는 클래스이므로 가짜 객체 생성을 도와주는 어노테이션 @Mock
    @Mock
    private SeedRepository seedRepository;

    private final String plantName = "해바라기";
    private final Integer growingPeriod = 4;
    private final String description = "사랑해바라기 !";
    private final AddSeedRequestDTO addSeedRequestDTO = new AddSeedRequestDTO(plantName, growingPeriod, description);

    @Test
    public void 씨앗등록실패_이미존재함() {

        // given
        doReturn(Optional.of(Seed.builder().build())).when(seedRepository).findByPlantName(plantName);

        // when
        final SeedException result = assertThrows(SeedException.class, () -> target.addSeed(addSeedRequestDTO));

        // then
        assertThat(result.getErrorResult()).isEqualTo(SeedErrorResult.DUPLICATED_SEED_REGISTER);

    }

    @Test
    public void 씨앗등록성공() {

        // given
        doReturn(Optional.empty()).when(seedRepository).findByPlantName(plantName);
        doReturn(seed()).when(seedRepository).save(any(Seed.class));

        // when
        final SeedResponseDTO result = target.addSeed(addSeedRequestDTO);

        // then
//        assertThat(result.getId()).isNotNull();
        assertThat(result.getPlantName()).isEqualTo("해바라기");

        // verify
        verify(seedRepository, times(1)).findByPlantName(plantName);
        verify(seedRepository, times(1)).save(any(Seed.class));

    }

    @Test
    public void 씨앗목록조회() {

        // given
        doReturn(Arrays.asList(
                Seed.builder().build(),
                Seed.builder().build(),
                Seed.builder().build()
        )).when(seedRepository).findAll();

        // when
        final List<SeedResponseDTO> result = target.getSeedList();

        // then
        assertThat(result.size()).isEqualTo(3);

    }

    @Test
    public void 씨앗상세조회실패_존재하지않음() {

        // given
        doReturn(Optional.empty()).when(seedRepository).findByPlantName(plantName);

        // when
        final SeedException result = assertThrows(SeedException.class, () -> target.getSeed(plantName));

        // then
        assertThat(result.getErrorResult()).isEqualTo(SeedErrorResult.SEED_NOT_FOUND);

    }

    @Test
    public void 씨앗상세조회성공() {

        // given
        doReturn(Optional.of(seed())).when(seedRepository).findByPlantName(plantName);

        // when
        final SeedResponseDTO result = target.getSeed(plantName);

        // then
        assertThat(result.getGrowingPeriod()).isEqualTo(growingPeriod);
        assertThat(result.getDescription()).isEqualTo(description);

    }

    private Seed seed() {

        return Seed.builder()
                .plantName("해바라기")
                .growingPeriod(4)
                .description("사랑해바라기 !")
                .build();

    }

}
