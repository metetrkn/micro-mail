package se.hitract.service;

import io.lettuce.core.output.ScanOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
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
    private final String apiKey;
    private final SentMailRepository sentMailRepository;


    public MailgunHttpService(RestClient.Builder builder,
                              @Value("${mailgun.api-key}") String apiKey,
                              @Value("${mailgun.domain}") String domain,
                              @Value("${mailgun.base-url}") String baseUrl,
                              SentMailRepository sentMailRepository,
                              PropertiesService propertiesService
    ) {
        this.domain = domain;
        this.apiKey = apiKey;
        this.sentMailRepository = sentMailRepository;

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

    public void send(MailRequestDTO request, String personal) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("from", personal + " <" + request.getFromMail() + ">");

        // Multiple recipient case
        if (request.getEmails().length > 0 && request.getEmails()[0] != null) {
            for (String recipient : request.getEmails()) {
                body.add("to", recipient);
            }
        } else {
            body.add("to", request.getEmail());
        }
        body.add("subject", request.getSubject());
        body.add("html", request.getContent());

        // Attachment handling
        if (request.getMailAttachments() != null && !request.getMailAttachments().isEmpty()) {
            for (MailAttachment attachment : request.getMailAttachments()) {
                body.add("attachment", new ByteArrayResource(attachment.getData()) {
                    @Override
                    public String getFilename() {
                        return attachment.getFilename();
                    }
                });
            }
        }

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
                try {
                    logSentMail(request, SENT_MAIL_STATUS.SUCCESS, "");
                } catch (Exception dbEx) {
                    log.error("Mail sent successfully, but DB recording failed: {}", dbEx.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("Failed to send mail: {}", e.getMessage());
            try {

                logSentMail(request, SENT_MAIL_STATUS.ERROR, e.toString());
            } catch (Exception dbException) {
                log.error("Could not record failure to DB: " + dbException.getMessage());
            }
            throw new RuntimeException("Mailgun delivery failure: " + e.getMessage(), e);
        }
    }

    protected void logSentMail(MailRequestDTO req, SENT_MAIL_STATUS status, String error) {
        SentMail mail = new SentMail();

        mail.setEmail(req.getEmail());
        mail.setStudentId(req.getStudentId());
        mail.setEntityId(req.getEntityId());

        if (req.getEntityType() != null) {
            mail.setEntityType(EntityType.valueOf(req.getEntityType()));
        }

        mail.setMailType(req.getMailType());
        mail.setSentMailStatus(status);
        mail.setError(error);

        sentMailRepository.save(mail);
    }

    public void send(MailRequestDTO request, String personal, String[] cc, String replyTo) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("from", personal + " <" + request.getFromMail() + ">");

        // Recipient handling
        for (String recipient : request.getEmails()) {
                body.add("to", recipient);
            }

        // CC handling
        if (cc != null) {
            for (String ccRecipient : cc) {
                if (ccRecipient != null) {
                    body.add("cc", ccRecipient);
                }
            }
        }

        // Reply-To handling
        if (replyTo != null) {
            body.add("h:Reply-To", replyTo);
        }

        body.add("subject", request.getSubject());
        body.add("html", request.getContent());

        // Attachment handling
        if (request.getMailAttachments() != null && !request.getMailAttachments().isEmpty()) {
            for (MailAttachment attachment : request.getMailAttachments()) {
                body.add("attachment", new ByteArrayResource(attachment.getData()) {
                    @Override
                    public String getFilename() {
                        return attachment.getFilename();
                    }
                });
            }
        }

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
                try {
                    logSentMail(request, SENT_MAIL_STATUS.SUCCESS, "");
                } catch (Exception dbEx) {
                    log.error("Mail sent successfully, but DB recording failed: {}", dbEx.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("Failed to send mail: {}", e.getMessage());
            try {
                logSentMail(request, SENT_MAIL_STATUS.ERROR, e.toString());
            } catch (Exception dbException) {
                log.error("Could not record failure to DB: " + dbException.getMessage());
            }
            throw new RuntimeException("Mailgun delivery failure: " + e.getMessage(), e);
        }
    }
}