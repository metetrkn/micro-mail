package se.hitract.service.mail.dto;

import lombok.*;
import se.hitract.model.domains.MAIL_TYPE;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailRequestDTO{
    private MAIL_TYPE mailType;
    private String email;
    private Map<String, Object> propertiesData;
    private String entityType;
    private Long entityId;
    private Long studentId;
    private String fromMail;
    private String subject;
    private String content;
    private boolean internalUser;
    private String firstName;


}

