package com._52org.gardenoftheheart.application.letter.controller;

import com._52org.gardenoftheheart.application.garden.error.GardenErrorCode;
import com._52org.gardenoftheheart.application.garden.error.GardenException;
import com._52org.gardenoftheheart.application.letter.dto.LetterDetailResponseDTO;
import com._52org.gardenoftheheart.application.letter.dto.LetterListResponseDTO;
import com._52org.gardenoftheheart.application.letter.dto.LetterRequestDTO;
import com._52org.gardenoftheheart.application.letter.dto.LetterResponseDTO;
import com._52org.gardenoftheheart.application.letter.error.LetterErrorCode;
import com._52org.gardenoftheheart.application.letter.error.LetterException;
import com._52org.gardenoftheheart.application.letter.service.LetterService;
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

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LetterControllerTest {

    @InjectMocks
    private LetterController target;

    @Mock
    private LetterService letterService;

    private MockMvc mockMvc;

    private Gson gson;

    @BeforeEach
    public void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        gson = new Gson();

    }

    @Test
    public void MockMvc가Null이아님() {

        assertThat(target).isNotNull();
        assertThat(mockMvc).isNotNull();

    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void 편지전송실패_잘못된파라미터(final String gardenId, final String plantName, final String author, final String message, final List<String> keywords) throws Exception {

        // given
        final String url = "/letter";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(sendLetterRequest(gardenId, plantName, author, message, keywords)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    public void 편지전송실패_LetterService에서에러Throw() throws Exception {

        // given
        final String url = "/letter";
        doThrow(new GardenException(GardenErrorCode.NON_EXISTENT_GARDEN))
                .when(letterService)
                .sendLetter(any(LetterRequestDTO.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(sendLetterRequest("123", "해바라기", "범키", "행복하세요 ~~~ !!!!!!!", List.of("키워드1", "키워드2"))))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isNotFound());

    }

    @Test
    public void 편지전송성공() throws Exception {

        // given
        final String url = "/letter";

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(sendLetterRequest("123", "해바라기", "범키", "행복하세요 ~~~ !!!!!!!", List.of("키워드1", "키워드2"))))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated());

    }

    @Test
    public void 편지목록조회실패_텃밭존재하지않음() throws Exception {

        // given
        final String url = "/letter/list/a123";
        doThrow(new GardenException(GardenErrorCode.NON_EXISTENT_GARDEN)).when(letterService).getLetterList("a123");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("uuid", "a123")
        );

        // then
        resultActions.andExpect(status().isNotFound());

    }

    @Test
    public void 편지목록조회성공() throws Exception {

        // given
        final String url = "/letter/list/a123";
        doReturn(List.of(
                LetterListResponseDTO.builder().build(),
                LetterListResponseDTO.builder().build(),
                LetterListResponseDTO.builder().build()
        )).when(letterService).getLetterList("a123");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("uuid", "a123")
        );

        // then
        resultActions.andExpect(status().isOk());

    }

    @Test
    public void 편지조회실패_존재하지않음() throws Exception {

        // given
        final String url = "/letter/1";
        doThrow(new LetterException(LetterErrorCode.NON_EXISTENT_LETTER)).when(letterService).getLetter(1L);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isNotFound());

    }

    @Test
    public void 편지조회성공() throws Exception {

        // given
        final String url = "/letter/1";
        doReturn(LetterResponseDTO.builder().build()).when(letterService).getLetter(1L);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isOk());

    }

    @Test
    public void 편지상세조회실패_존재하지않음() throws Exception {

        // given
        final String url = "/letter/detail/1";
        doThrow(new LetterException(LetterErrorCode.NON_EXISTENT_LETTER)).when(letterService).getLetterDetail(1L);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isNotFound());

    }

    @Test
    public void 편지상세조회성공() throws Exception {

        // given
        final String url = "/letter/detail/1";
        doReturn(LetterDetailResponseDTO.builder().build()).when(letterService).getLetterDetail(1L);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isOk());

    }

    private static Stream<Arguments> invalidParameters() {

        return Stream.of(
                Arguments.of(null, "해바라기", "범키", "행복하세요 ~~~ !!!!!!!", List.of("키워드1", "키워드2")),
                Arguments.of("123", null, "범키", "행복하세요 ~~~ !!!!!!!", List.of("키워드1", "키워드2")),
                Arguments.of("123", "해바라기", null, "행복하세요 ~~~ !!!!!!!", List.of("키워드1", "키워드2")),
                Arguments.of("123", "해바라기", "범키", null, List.of("키워드1", "키워드2")),
                Arguments.of("123", "해바라기", "범키", "행복하세요 ~~~ !!!!!!!", null)
        );

    }

    private LetterRequestDTO sendLetterRequest(final String gardenId, final String plantName, final String author, final String message, final List<String> keywords) {

        return LetterRequestDTO.builder()
                .gardenId(gardenId)
                .plantName(plantName)
                .author(author)
                .message(message)
                .keywords(keywords)
                .build();

    }

}
