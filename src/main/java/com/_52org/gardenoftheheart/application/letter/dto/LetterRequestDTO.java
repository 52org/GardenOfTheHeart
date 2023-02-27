package com._52org.gardenoftheheart.application.letter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class LetterRequestDTO {

    @NotBlank
    private final String gardenId;

    @NotBlank
    private final String plantName;

    @NotBlank
    private final String author;

    @NotBlank
    private final String message;

    @NotNull
    private final List<String> keywords;

}
