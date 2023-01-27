package com._52org.gardenoftheheart.application.garden.service;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import com._52org.gardenoftheheart.application.garden.dto.CreateGardenRequestDTO;
import com._52org.gardenoftheheart.application.garden.dto.GardenResponseDTO;
import com._52org.gardenoftheheart.application.garden.error.GardenErrorCode;
import com._52org.gardenoftheheart.application.garden.error.GardenException;
import com._52org.gardenoftheheart.application.garden.repository.GardenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GardenServiceTest {

    @InjectMocks
    private GardenService target;

    @Mock
    private GardenRepository gardenRepository;

    private final String gardenName = "재이";
    private final String password = "제이름은요";
    private final CreateGardenRequestDTO createGardenRequestDTO = new CreateGardenRequestDTO(gardenName, password);

    @Test
    public void 텃밭생성실패_이미존재함() {

        // given
        doReturn(Optional.of(Garden.builder().build())).when(gardenRepository).findByGardenName(gardenName);

        // when
        final GardenException result = assertThrows(GardenException.class, () -> target.createGarden(createGardenRequestDTO));

        // then
        assertThat(result.getErrorCode()).isEqualTo(GardenErrorCode.DUPLICATED_GARDENNAME);

    }

    @Test
    public void 텃밭생성성공() {

        // given
        doReturn(Optional.empty()).when(gardenRepository).findByGardenName(gardenName);
        doReturn(gardener()).when(gardenRepository).save(any(Garden.class));

        // when
        final GardenResponseDTO result = target.createGarden(createGardenRequestDTO);

        // then
//        assertThat(result.getId()).isNotNull();
        assertThat(result.getUuid()).isNotNull();

        // verify
        verify(gardenRepository, times(1)).findByGardenName(gardenName);
        verify(gardenRepository, times(1)).save(any(Garden.class));

    }

    private Garden gardener() {

        return Garden.builder()
                .gardenName(gardenName)
                .password(password)
                .uuid(UUID.randomUUID().toString())
                .build();

    }

}
