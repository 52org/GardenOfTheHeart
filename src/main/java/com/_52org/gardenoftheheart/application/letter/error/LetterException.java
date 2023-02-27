package com._52org.gardenoftheheart.application.letter.error;

import com._52org.gardenoftheheart.error.BaseException;
import lombok.Getter;

@Getter
public class LetterException extends BaseException {

    public LetterException(final LetterErrorCode errorCode) {
        super(errorCode);
    }

}
