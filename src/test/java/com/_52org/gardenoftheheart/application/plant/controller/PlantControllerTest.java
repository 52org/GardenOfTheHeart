package com._52org.gardenoftheheart.application.plant.controller;

import com._52org.gardenoftheheart.application.plant.api.PlantController;
import com._52org.gardenoftheheart.application.plant.dto.PlantRequestDTO;
import com._52org.gardenoftheheart.application.plant.dto.PlantResponseDTO;
import com._52org.gardenoftheheart.application.plant.error.PlantErrorCode;
import com._52org.gardenoftheheart.application.plant.error.PlantException;
import com._52org.gardenoftheheart.application.plant.service.PlantService;
import com._52org.gardenoftheheart.error.GlobalExceptionHandler;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PlantControllerTest {

    @InjectMocks
    private PlantController target;

    @Mock
    private PlantService plantService;

    private MockMvc mockMvc;

    private Gson gson;

    @BeforeEach
    public void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        gson = new Gson();

    }

    @Test
    public void MockMvc가Null이아님() throws Exception {

        assertThat(target).isNotNull();
        assertThat(mockMvc).isNotNull();

    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void 씨앗등록실패_잘못된파라미터(final String plantName, final Integer growingPeriod, final String description) throws Exception {

        // given
        final String url = "/plant";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(addPlantRequest(plantName, growingPeriod, description)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    public void 씨앗등록실패_PlantService에서에러Throw() throws Exception {

        // given
        final String url = "/plant";
        doThrow(new PlantException(PlantErrorCode.DUPLICATED_PLANTNAME))
                .when(plantService)
                .addPlant(any(PlantRequestDTO.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(addPlantRequest("해바라기", 4, "사랑해바라기 !")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isConflict());

    }

    @Test
    public void 씨앗등록성공() throws Exception {

        // given
        final String url = "/plant";
        final PlantResponseDTO plantResponseDTO = PlantResponseDTO.builder()
                .plantName("해바라기")
                .growingPeriod(4)
                .description("사랑해바라기 !")
                .build();

        doReturn(plantResponseDTO).when(plantService).addPlant(any(PlantRequestDTO.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(addPlantRequest("해바라기", 4, "사랑해바라기 !")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated());

        final PlantResponseDTO response = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), PlantResponseDTO.class);

        assertThat(response.getPlantName()).isEqualTo("해바라기");
        assertThat(response.getGrowingPeriod()).isEqualTo(4);

    }

    @Test
    public void 씨앗목록조회성공() throws Exception {

        // given
        final String url = "/plant";
        doReturn(Arrays.asList(
                PlantResponseDTO.builder().build(),
                PlantResponseDTO.builder().build(),
                PlantResponseDTO.builder().build()
        )).when(plantService).getPlantList();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isOk());

    }

    @Test
    public void 씨앗상세조회실패_존재하지않음() throws Exception {

        // given
        final String url = "/plant/해바라기";
        doThrow(new PlantException(PlantErrorCode.NON_EXISTENT_PLANT)).when(plantService).getPlant("해바라기");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isNotFound());

    }

    @Test
    public void 씨앗상세조회성공() throws Exception {

        // given
        final String url = "/plant/해바라기";
        doReturn(PlantResponseDTO.builder().build()).when(plantService).getPlant("해바라기");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("plantName", "해바라기")
        );

        // then
        resultActions.andExpect(status().isOk());

    }

    private static Stream<Arguments> invalidParameters() {

        return Stream.of(
                Arguments.of(null, 4, "사랑해바라기 !"),
                Arguments.of("해바라기", -1, "사랑해바라기 !"),
                Arguments.of("해바라기", 4, null)
        );

    }

    private PlantRequestDTO addPlantRequest(final String plantName, final Integer growingPeriod, final String description) {

        return PlantRequestDTO.builder()
                .plantName(plantName)
                .growingPeriod(growingPeriod)
                .description(description)
                .build();

    }

}
