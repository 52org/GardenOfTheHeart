package com._52org.gardenoftheheart.exception.seed;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SeedException extends RuntimeException {

    private final SeedErrorResult errorResult;

}
