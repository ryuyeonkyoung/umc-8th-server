package org.umc.spring.apiPayload.exception.handler;

import org.umc.spring.apiPayload.code.BaseErrorCode;
import org.umc.spring.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
