package com._52org.gardenoftheheart.application.seed.controller;

import com._52org.gardenoftheheart.application.seed.api.SeedController;
import com._52org.gardenoftheheart.application.seed.dto.AddSeedRequestDTO;
import com._52org.gardenoftheheart.application.seed.dto.SeedResponseDTO;
import com._52org.gardenoftheheart.application.seed.error.SeedErrorCode;
import com._52org.gardenoftheheart.application.seed.error.SeedException;
import com._52org.gardenoftheheart.application.seed.service.SeedService;
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
public class SeedControllerTest {

    @InjectMocks
    private SeedController target;

    @Mock
    private SeedService seedService;

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
        final String url = "/seed";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(addSeedRequest(plantName, growingPeriod, description)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    public void 씨앗등록실패_SeedService에서에러Throw() throws Exception {

        // given
        final String url = "/seed";
        doThrow(new SeedException(SeedErrorCode.DUPLICATED_PLANTNAME))
                .when(seedService)
                .addSeed(any(AddSeedRequestDTO.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(addSeedRequest("해바라기", 4, "사랑해바라기 !")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    public void 씨앗등록성공() throws Exception {

        // given
        final String url = "/seed";
        final SeedResponseDTO seedResponseDTO = SeedResponseDTO.builder()
                .plantName("해바라기")
                .growingPeriod(4)
                .description("사랑해바라기 !")
                .build();

        doReturn(seedResponseDTO).when(seedService).addSeed(any(AddSeedRequestDTO.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(addSeedRequest("해바라기", 4, "사랑해바라기 !")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated());

        final SeedResponseDTO response = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), SeedResponseDTO.class);

        assertThat(response.getPlantName()).isEqualTo("해바라기");
        assertThat(response.getGrowingPeriod()).isEqualTo(4);

    }

    @Test
    public void 씨앗목록조회성공() throws Exception {

        // given
        final String url = "/seed";
        doReturn(Arrays.asList(
                SeedResponseDTO.builder().build(),
                SeedResponseDTO.builder().build(),
                SeedResponseDTO.builder().build()
        )).when(seedService).getSeedList();

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
        final String url = "/seed/해바라기";
        doThrow(new SeedException(SeedErrorCode.NOT_EXIST_SEED)).when(seedService).getSeed("해바라기");

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
        final String url = "/seed/해바라기";
        doReturn(SeedResponseDTO.builder().build()).when(seedService).getSeed("해바라기");

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

    private AddSeedRequestDTO addSeedRequest(final String plantName, final Integer growingPeriod, final String description) {

        return AddSeedRequestDTO.builder()
                .plantName(plantName)
                .growingPeriod(growingPeriod)
                .description(description)
                .build();

    }

}
