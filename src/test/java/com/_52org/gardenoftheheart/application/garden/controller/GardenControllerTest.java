package com._52org.gardenoftheheart.application.garden.controller;

import com._52org.gardenoftheheart.application.garden.api.GardenController;
import com._52org.gardenoftheheart.application.garden.dto.GardenRequestDTO;
import com._52org.gardenoftheheart.application.garden.dto.GardenSignUpResponseDTO;
import com._52org.gardenoftheheart.application.garden.error.GardenErrorCode;
import com._52org.gardenoftheheart.application.garden.error.GardenException;
import com._52org.gardenoftheheart.application.garden.service.GardenService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class GardenControllerTest {

    @InjectMocks
    GardenController target;

    @Mock
    GardenService gardenService;

    private MockMvc mockMvc;

    private Gson gson;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        gson = new Gson();

        passwordEncoder = new BCryptPasswordEncoder();

    }

    @Test
    public void MockMvc가Null이아님() {

        assertThat(target).isNotNull();
        assertThat(mockMvc).isNotNull();

    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void 텃밭생성실패_잘못된파라미터(final String gardenName, final String password) throws Exception {

        // given
        final String url = "/garden/join";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(gardenRequest(gardenName, password)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    public void 텃밭생성실패_GardenService에서에러Throw() throws Exception {

        // given
        final String url = "/garden/join";

        doThrow(new GardenException(GardenErrorCode.DUPLICATED_GARDENNAME))
                .when(gardenService)
                .createGarden(any(GardenRequestDTO.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(gardenRequest("재이", "제이름은요")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isConflict());

    }

    @Test
    public void 텃밭생성성공() throws Exception {

        // given
        final String url = "/garden/join";
        final GardenSignUpResponseDTO gardenSignUpResponseDTO = GardenSignUpResponseDTO.builder()
                .uuid(UUID.randomUUID().toString())
                .build();

        doReturn(gardenSignUpResponseDTO).when(gardenService).createGarden(any(GardenRequestDTO.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(gardenRequest("재이", "제이름은요")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated());

        final GardenSignUpResponseDTO response = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), GardenSignUpResponseDTO.class);

        assertThat(response.getUuid()).isNotNull();

    }

    @Test
    public void 로그인실패_gardenName이존재하지않음() throws Exception {

        // given
        final String url = "/garden/login";
        doThrow(new GardenException(GardenErrorCode.NON_EXISTENT_GARDEN)).when(gardenService).login(any(GardenRequestDTO.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .with(csrf())
                        .content(gson.toJson(gardenRequest("제이", "제이름은요")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isNotFound());

    }

    @Test
    public void 로그인실패_password가틀림() throws Exception {

        // given
        final String url = "/garden/login";
        doThrow(new GardenException(GardenErrorCode.INVALID_PASSWORD)).when(gardenService).login(any(GardenRequestDTO.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .with(csrf())
                        .content(gson.toJson(gardenRequest("제이", "제이름은요")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isUnauthorized());

    }

    @Test
    public void 로그인성공() throws Exception {

        // given
        final String url = "/garden/login";
        final GardenSignUpResponseDTO gardenSignUpResponseDTO = GardenSignUpResponseDTO.builder()
                .uuid(UUID.randomUUID().toString())
                .build();

        doReturn("token").when(gardenService).login(any(GardenRequestDTO.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .with(csrf())
                        .content(gson.toJson(gardenRequest("재이", "제이름은요")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());

//        final GardenResponseDTO response = gson.fromJson(resultActions.andReturn()
//                .getResponse()
//                .getContentAsString(StandardCharsets.UTF_8), GardenResponseDTO.class);
//
//        assertThat(response.getUuid()).isNotNull();

    }

    private static Stream<Arguments> invalidParameters() {

        return Stream.of(
                Arguments.of(null, "제이름은요"),
                Arguments.of("재이", null)
        );

    }

    private GardenRequestDTO gardenRequest(final String gardenName, final String password) {

        return GardenRequestDTO.builder()
                .gardenName(gardenName)
                .password(passwordEncoder.encode(password))
                .build();

    }

}
