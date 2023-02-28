package com._52org.gardenoftheheart.application.plant.error;

import com._52org.gardenoftheheart.error.BaseException;
import lombok.Getter;

@Getter
public class PlantException extends BaseException {

    public PlantException(final PlantErrorCode errorCode) {
        super(errorCode);
    }

}
