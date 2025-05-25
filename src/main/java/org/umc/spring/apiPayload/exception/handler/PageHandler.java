package org.umc.spring.apiPayload.exception.handler;

import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.GeneralException;

public class PageHandler extends GeneralException {
    public PageHandler(ErrorStatus status) {
        super(status);
    }
}