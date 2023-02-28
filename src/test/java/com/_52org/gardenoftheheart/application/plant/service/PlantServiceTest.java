package com._52org.gardenoftheheart.application.plant.service;

import com._52org.gardenoftheheart.application.plant.domain.Plant;
import com._52org.gardenoftheheart.application.plant.dto.PlantRequestDTO;
import com._52org.gardenoftheheart.application.plant.dto.PlantResponseDTO;
import com._52org.gardenoftheheart.application.plant.error.PlantErrorCode;
import com._52org.gardenoftheheart.application.plant.error.PlantException;
import com._52org.gardenoftheheart.application.plant.repository.PlantRepository;
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
public class PlantServiceTest {

    // 테스트 대상이므로 의존성이 주입되는 어노테이션 @InjectMocks
    @InjectMocks
    private PlantService target;

    // 의존성이 있는 클래스이므로 가짜 객체 생성을 도와주는 어노테이션 @Mock
    @Mock
    private PlantRepository plantRepository;

    private final String plantName = "해바라기";
    private final Integer growingPeriod = 4;
    private final String description = "사랑해바라기 !";
    private final PlantRequestDTO plantRequestDTO = new PlantRequestDTO(plantName, growingPeriod, description);

    @Test
    public void 씨앗등록실패_이미존재함() {

        // given
        doReturn(Optional.of(Plant.builder().build())).when(plantRepository).findByPlantName(plantName);

        // when
        final PlantException result = assertThrows(PlantException.class, () -> target.addPlant(plantRequestDTO));

        // then
        assertThat(result.getErrorCode()).isEqualTo(PlantErrorCode.DUPLICATED_PLANTNAME);

    }

    @Test
    public void 씨앗등록성공() {

        // given
        doReturn(Optional.empty()).when(plantRepository).findByPlantName(plantName);
        doReturn(plant()).when(plantRepository).save(any(Plant.class));

        // when
        final PlantResponseDTO result = target.addPlant(plantRequestDTO);

        // then
//        assertThat(result.getId()).isNotNull();
        assertThat(result.getPlantName()).isEqualTo(plantName);

        // verify
        verify(plantRepository, times(1)).findByPlantName(plantName);
        verify(plantRepository, times(1)).save(any(Plant.class));

    }

    @Test
    public void 씨앗목록조회() {

        // given
        doReturn(Arrays.asList(
                Plant.builder().build(),
                Plant.builder().build(),
                Plant.builder().build()
        )).when(plantRepository).findAll();

        // when
        final List<PlantResponseDTO> result = target.getPlantList();

        // then
        assertThat(result.size()).isEqualTo(3);

    }

    @Test
    public void 씨앗상세조회실패_존재하지않음() {

        // given
        doReturn(Optional.empty()).when(plantRepository).findByPlantName(plantName);

        // when
        final PlantException result = assertThrows(PlantException.class, () -> target.getPlant(plantName));

        // then
        assertThat(result.getErrorCode()).isEqualTo(PlantErrorCode.NON_EXISTENT_PLANT);

    }

    @Test
    public void 씨앗상세조회성공() {

        // given
        doReturn(Optional.of(plant())).when(plantRepository).findByPlantName(plantName);

        // when
        final PlantResponseDTO result = target.getPlant(plantName);

        // then
        assertThat(result.getGrowingPeriod()).isEqualTo(growingPeriod);
        assertThat(result.getDescription()).isEqualTo(description);

    }

    private Plant plant() {

        return Plant.builder()
                .plantName(plantName)
                .growingPeriod(growingPeriod)
                .description(description)
                .build();

    }

}
