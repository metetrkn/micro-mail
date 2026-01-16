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
        try {
            log.info("START: Processing email for: {}", emailDto.getToMailList());
            mailRoutingService.routeEmail(emailDto);
            log.info("SUCCESS: Sent to: {}", emailDto.getToMailList());
        } catch (Exception e) {
            // Log this as DEBUG or WARN so it doesn't clutter your failure file
            log.warn("Attempt failed for {}. Rqueue will retry.", emailDto.getToMail());
            throw e; // This tells Rqueue to retry
        }
    }

    @RqueueListener(value = "mail-events-dlq")
    public void logDeadLetterMessages(Object emailDto) {
        try {
            String jsonFailure = objectMapper.writeValueAsString(emailDto);
            // THIS is where you log the FATAL error.
            // It only runs ONCE, after all 3 retries have failed.
            log.error("FINAL_FAILURE_JSON: {}", jsonFailure);
        } catch (Exception e) {
            log.error("FATAL: Could not serialize failed mail.");
        }
    }
}