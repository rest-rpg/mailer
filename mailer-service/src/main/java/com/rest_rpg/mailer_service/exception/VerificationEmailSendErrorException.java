package com.rest_rpg.mailer_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VerificationEmailSendErrorException extends ResponseStatusException {

    public static final String ERROR_CODE = "VERIFICATION_EMAIL_SEND_ERROR";

    public VerificationEmailSendErrorException() {
        super(HttpStatus.FORBIDDEN, ERROR_CODE);
    }
}
