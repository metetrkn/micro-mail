package se.hitract.service.mail.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import se.hitract.model.enums.MAIL_TYPE;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailRequestDTO{
    private MAIL_TYPE mailType;
    private String toMail;
    private List<String> toMailList;
    private String token;
    private String language;
    private String newSite;
    private String entityType;
    private Long entityId;
    private Long student_id;
    private String fromMail;
    private String subject;

}

