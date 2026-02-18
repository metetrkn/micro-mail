package se.hitract.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sonus21.rqueue.annotation.RqueueListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.hitract.model.domains.MAIL_TYPE;
import se.hitract.service.mail.dto.MailRequestDTO;
import se.hitract.service.MailRoutingService;


@Component
@Slf4j
@RequiredArgsConstructor
public class EmailConsumer {

    private final MailRoutingService mailRoutingService;
    private final ObjectMapper objectMapper;

    @RqueueListener(
            value = "mail-events",
            deadLetterQueue = "mail-events-dlq",
            numRetries = "3",
            concurrency = "20-50"
    )

    public void processEmail(MailRequestDTO emailDto) {

        boolean isJonkoping = emailDto.getMailType() == MAIL_TYPE.JONKOPING_PAY_MEMBERSHIP_MAIL;

        boolean hasSingleRecipient = emailDto.getEmail() != null && !emailDto.getEmail().trim().isEmpty();
        boolean hasMultipleRecipient = emailDto.getEmails() != null && emailDto.getEmails().length > 0;

        if (!isJonkoping && !hasSingleRecipient && !hasMultipleRecipient) {
            log.warn("SWALLOWED: No recipient address for MailType {}. Skipping to prevent API/DB errors.", emailDto.getMailType());
            return;
        }

        try {
            log.info("START: Processing email for type: {} (Email: {})", emailDto.getMailType(), emailDto.getEmail());
            mailRoutingService.routeEmail(emailDto);
            log.info("SUCCESS: Sent/Processed type: {}", emailDto.getMailType());
        } catch (Exception e) {
            log.error("SYSTEM ERROR: Attempt failed for {}. Rqueue will retry.", emailDto.getMailType(), e);
            throw e;
        }
    }

    @RqueueListener(value = "mail-events-dlq")
    public void logDeadLetterMessages(Object emailDto) {
        try {
            String jsonFailure = objectMapper.writeValueAsString(emailDto);
            log.error("FINAL_FAILURE_JSON: {}", jsonFailure);
        } catch (Exception e) {
            log.error("FATAL: Could not serialize failed mail.");
        }
    }
}