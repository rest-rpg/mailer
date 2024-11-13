package com.rest_rpg.mailer_service.service;

import com.rest_rpg.mailer_api.model.SendVerificationEmailEvent;
import com.rest_rpg.mailer_service.exception.VerificationEmailSendErrorException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Service
@Slf4j
public class MailingService {

    private final JavaMailSender mailSender;

    @KafkaListener(topics = SendVerificationEmailEvent.TOPIC_NAME)
    public void sendAccountVerificationEmail(@NotNull @Valid SendVerificationEmailEvent event) {
        String toAddress = event.getEmail();

        String subject = "Please verify your registration";
        String content = String.format("Dear %s,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"%s\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Rest RPG.", event.getUserName(), event.getVerificationUrl());
        sendEmail(toAddress, subject, content);
    }

    private void sendEmail(@NotBlank String email, @NotBlank String subject, @NotBlank String body) {
        String fromAddress = "server@restrpg.com";
        String senderName = "RPG";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(email);
            helper.setSubject(subject);

            helper.setText(body, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new VerificationEmailSendErrorException();
        }

        mailSender.send(message);
    }
}
