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
            log.info("START: Processing email for: {}", emailDto.getToMail());
            mailRoutingService.routeEmail(emailDto);
            log.info("SUCCESS: Sent to: {}", emailDto.getToMail());
        } catch (Exception e) {
            log.warn("Attempt failed for {}. Rqueue will retry.", emailDto.getToMail());
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