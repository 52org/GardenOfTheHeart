package com._52org.gardenoftheheart.application.garden.error;

import com._52org.gardenoftheheart.error.BaseException;
import lombok.Getter;

@Getter
public class GardenException extends BaseException {

    public GardenException(final GardenErrorCode errorCode) {
        super(errorCode);
    }

}
