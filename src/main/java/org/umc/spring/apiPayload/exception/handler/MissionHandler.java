package org.umc.spring.apiPayload.exception.handler;

import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.GeneralException;

public class MissionHandler extends GeneralException {
    public MissionHandler(ErrorStatus status) {
        super(status);
    }
}