package se.hitract.service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class WireMockInitializer implements CommandLineRunner {

    @Value("${mailgun.base-url}")
    private String wiremockBaseUrl;

    @Value("${mailgun.domain}")
    private String domain;

    @Override
    public void run(String... args) {
        int maxAttempts = 5;
        boolean success = false;

        for (int i = 0; i < maxAttempts; i++) {
            try {
                success = registerMailgunStub();
                if (success) break;
            } catch (Exception e) {
                System.err.println("[WireMock] Attempt " + (i + 1) + " failed: WireMock not ready yet. Retrying...");
                try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
            }
        }

        if (!success) {
            System.err.println("CRITICAL: WireMock stubbing failed after " + maxAttempts + " attempts.");
        }
    }

    private boolean registerMailgunStub() throws Exception {
        // MATCH THIS URL to your MailgunHttpService logic: /{domain}/messages
        // Note: domain is injected from your properties (sandbox.hitract.se)
        String jsonPayload = """
        {
          "priority": 1,
          "request": {
            "method": "POST",
            "url": "/%s/messages"
          },
          "response": {
            "status": 200,
            "headers": { "Content-Type": "application/json" },
            "jsonBody": {
              "id": "<static-mock-id@hitract.se>",
              "message": "Queued. Thank you.",
              "status": "success"
            }
          }
        }
        """.formatted(domain);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        // Standard WireMock Admin API endpoint
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(wiremockBaseUrl + "/__admin/mappings"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            System.out.println("[WireMock] SUCCESS: Registered stub for domain: " + domain);
            return true;
        } else {
            System.err.println("[WireMock] FAILED to register stub. Status: " + response.statusCode());
            return false;
        }
    }
}