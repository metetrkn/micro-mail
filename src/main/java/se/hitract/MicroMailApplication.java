package se.hitract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroMailApplication {

    public static void main(String[] args) {
        // 1. Run WireMock configuration before or after app start
        configureWireMock();

        SpringApplication.run(MicroMailApplication.class, args);
    }

    private static void configureWireMock() {
        try {
            // MATCH THIS URL to your MailgunHttpService logic: /{domain}/messages
            String jsonPayload = """
        {
          "priority": 1,
          "request": {
            "method": "POST",
            "url": "/sandbox.hitract.se/messages"
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
        """;

            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create("http://localhost:9090/__admin/mappings"))
                    .header("Content-Type", "application/json")
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            java.net.http.HttpResponse<String> response = client.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                System.out.println("[WireMock] SUCCESS: Stub registered for /sandbox.hitract.se/messages");
            } else {
                System.out.println("[WireMock] FAILED: Status Code " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("[WireMock] ERROR: Could not connect to Docker on 9090");
        }
    }
}