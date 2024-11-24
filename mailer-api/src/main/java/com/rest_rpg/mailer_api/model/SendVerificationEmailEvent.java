package com.rest_rpg.mailer_api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SendVerificationEmailEvent {

    public static final String TOPIC_NAME = "send-verification-email";

    private @NotBlank String userName;
    private @NotBlank String email;
    private @NotBlank String verificationUrl;
}
