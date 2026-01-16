package se.hitract.service;

import io.lettuce.core.output.ScanOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

// Project-specific imports (Adjust package names based on your project structure)
import se.hitract.model.MailgunResponse;
import se.hitract.model.enums.EntityType;
import se.hitract.service.mail.dto.MailRequestDTO;
import se.hitract.model.SentMail;
import se.hitract.repository.SentMailRepository;
import se.hitract.model.enums.SENT_MAIL_STATUS;

@Service
@Slf4j
public class MailgunHttpService {

    private final RestClient restClient;
    private final String domain;
    private final String apiKey; // Class field to make it accessible in all methods
    private final SentMailRepository sentMailRepository;
    private final PropertiesService propertiesService;

    public MailgunHttpService(RestClient.Builder builder,
                              @Value("${mailgun.api-key}") String apiKey,
                              @Value("${mailgun.domain}") String domain,
                              @Value("${mailgun.base-url}") String baseUrl,
                              SentMailRepository sentMailRepository,
                              PropertiesService propertiesService) {
        this.domain = domain;
        this.apiKey = apiKey; // Assigning the constructor parameter to the class field
        this.sentMailRepository = sentMailRepository;
        this.propertiesService = propertiesService;

        this.restClient = builder
                .baseUrl(baseUrl)
                .requestFactory(new org.springframework.http.client.JdkClientHttpRequestFactory(
                        java.net.http.HttpClient.newBuilder()
                                .version(java.net.http.HttpClient.Version.HTTP_1_1) // Force HTTP 1.1
                                .build()
                ))
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new RuntimeException("Mailgun API Error: " + response.getStatusCode());
                })
                .build();
    }

    @Async
    public void send(MailRequestDTO request) {
//        if (!shouldSend(request)) return;

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("from", "Hitract <" + request.getFromMail() + ">");
        body.add("to", request.getToMail());
        body.add("subject", request.getSubject());
        body.add("html", request.getContent());

        try {
            MailgunResponse response = restClient.post()
                    .uri("{domain}/messages", domain)
                    .headers(headers -> headers.setBasicAuth("api", apiKey))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(body)
                    .retrieve()
                    .body(MailgunResponse.class);

            if (response != null) {
                System.out.printf("WireMock/Mailgun Success: ID=%s Message=%s%n", response.id(), response.message());
                logSentMail(request, SENT_MAIL_STATUS.SUCCESS, "Mailgun ID: " + response.id());
            }

         } catch (Exception e) {
            log.error("Failed to send mail: {}", e.getMessage());
            logSentMail(request, SENT_MAIL_STATUS.ERROR, e.toString());
        }
    }

//    private boolean shouldSend(MailRequestDTO request) {
//        // Use getToMail() instead of getTo()
//        String recipient = request.getToMail();
//        if (recipient == null) return false;
//
//        boolean isWhitelisted = recipient.contains("hitract.se")
//                || recipient.contains("hitract.com")
//                || recipient.contains("nordstrom.robert@gmail.com");
//
//        return "PROD".equals(propertiesService.getEnvironment()) || isWhitelisted;
//    }

    private void logSentMail(MailRequestDTO req, SENT_MAIL_STATUS status, String error) {
        // 1. Check if entityType string is present
        EntityType typeEnum = null;
        try {
            if (req.getEntityType() != null && !req.getEntityType().isEmpty()) {
                // 2. Convert the String from DTO to the Enum expected by SentMail
                typeEnum = EntityType.valueOf(req.getEntityType());
            }
        } catch (IllegalArgumentException e) {
            log.warn("Invalid EntityType string received: {}", req.getEntityType());
        }

        // 3. Pass the converted Enum (typeEnum) instead of the String
        sentMailRepository.save(new SentMail(
                status,
                req.getToMail(),
                null,
                req.getMailType(),
                error,
                typeEnum,
                req.getEntityId()
        ));
    }

}