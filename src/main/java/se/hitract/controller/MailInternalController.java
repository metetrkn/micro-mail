package se.hitract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.hitract.service.MailRoutingService;
import se.hitract.service.mail.dto.MailRequestDTO;

@RestController
@RequestMapping("/api/internal/mail")
@RequiredArgsConstructor
public class MailInternalController {

    private final MailRoutingService mailRoutingService;

    @PostMapping("/send")
    public ResponseEntity<Void> receiveMailRequest(@RequestBody MailRequestDTO request) {

        mailRoutingService.routeEmail(request);

        return ResponseEntity.ok().build();
    }
}