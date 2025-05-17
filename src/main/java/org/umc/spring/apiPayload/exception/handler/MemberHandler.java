package org.umc.spring.apiPayload.exception.handler;

import org.umc.spring.apiPayload.code.status.ErrorStatus;
import org.umc.spring.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(ErrorStatus status) {
        super(status);
    }
}
