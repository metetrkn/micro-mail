//package se.hitract.mail.services;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.net.URI;
//import java.net.URLEncoder;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.stream.Collectors;
//
//public class EmailSender {
//
//    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
//    private static final String MAILGUN_URL = "http://localhost:8080/v1/send-email";
//    private static final String API_KEY = "api:key-fake";
//    private static final String FROM_EMAIL = "sender@example.com";
//
//    private static final HttpClient client = HttpClient.newBuilder()
//            .version(HttpClient.Version.HTTP_2)
//            .connectTimeout(Duration.ofSeconds(10))
//            .build();
//
//    // 1. UPDATE: Add topic and consumerId to the data structure
//    public static class EmailRequest {
//        public String to;
//        public String subject;
//        public String body;
//        public long creationTime;
//        public String topic;      // NEW
//        public int consumerId;    // NEW
//
//        public EmailRequest(String to, String subject, String body, long creationTime, String topic, int consumerId) {
//            this.to = to;
//            this.subject = subject;
//            this.body = body;
//            this.creationTime = creationTime;
//            this.topic = topic;
//            this.consumerId = consumerId;
//        }
//    }
//
//    public static void sendBatch(List<EmailRequest> batch) {
//        if (batch == null || batch.isEmpty()) return;
//
//        List<CompletableFuture<Void>> futures = batch.stream()
//                .map(email -> sendAsync(email))
//                .collect(Collectors.toList());
//
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//    }
//
//    private static CompletableFuture<Void> sendAsync(EmailRequest email) {
//        String formData = buildFormData(email.to, email.subject, email.body);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(MAILGUN_URL))
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(API_KEY.getBytes()))
//                .POST(HttpRequest.BodyPublishers.ofString(formData))
//                .timeout(Duration.ofSeconds(10))
//                .build();
//
//        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenAccept(response -> {
//                    if (response.statusCode() == 200) {
//                        long sentTime = System.currentTimeMillis();
//
//                        logger.info("Topic: {} | Consumer: {} | Sent to: {} | Created: {} | Sent: {}",
//                                email.topic, email.consumerId, email.to, email.creationTime, sentTime);
//                    } else {
//                        logger.error("Failed: {} | Status: {}", email.to, response.statusCode());
//                    }
//                })
//                .exceptionally(ex -> {
//                    logger.error("Error sending to {}: {}", email.to, ex.getMessage());
//                    return null;
//                });
//    }
//
//    private static String buildFormData(String to, String subject, String body) {
//        return "from=" + encode(FROM_EMAIL) +
//                "&to=" + encode(to) +
//                "&subject=" + encode(subject) +
//                "&text=" + encode(body);
//    }
//
//    private static String encode(String value) {
//        return URLEncoder.encode(value, StandardCharsets.UTF_8);
//    }
//}