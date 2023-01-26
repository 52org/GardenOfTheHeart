package com._52org.gardenoftheheart.application.seed.error;

import com._52org.gardenoftheheart.error.BaseException;
import lombok.Getter;

@Getter
public class SeedException extends BaseException {

    public SeedException(final SeedErrorCode errorCode) {
        super(errorCode);
    }

}
