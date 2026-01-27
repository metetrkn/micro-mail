package se.hitract.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sonus21.rqueue.annotation.RqueueListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

        // 1. Check if the email is missing, if so logs it but system continues running
        if (emailDto.getEmail() == null || emailDto.getEmail().trim().isEmpty()) {
            log.warn("SWALLOWED: No email address for ID {}. Skipping processing.", emailDto.getStudentId());
            return;
        }

        // 3. Normal process continues only if the check above passes
        try {
            log.info("START: Processing email for: {}", emailDto.getEmail());
            mailRoutingService.routeEmail(emailDto);
            log.info("SUCCESS: Sent to: {}", emailDto.getEmail());
        } catch (Exception e) {
            // We only catch and rethrow real system errors (like the mail server being down)
            log.error("SYSTEM ERROR: Attempt failed for {}. Rqueue will retry.", emailDto.getEmail());
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