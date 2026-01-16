package se.hitract.service.mail.dto;

import lombok.*;
import se.hitract.model.enums.MAIL_TYPE;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailRequestDTO extends CommonDTO {
    private MAIL_TYPE mailType;
    private String toMail;
    private List<String> toMailList;
    private Map<String, Object> propertiesData;
    private String entityType;
    private Long entityId;
    private Long sent_mail_id;
    private Long student_id;
    private String fromMail;
    private String subject;
    private String content;
    private boolean internalUser;

    @Override
    public Long getId() {
        return this.sent_mail_id;
    }
}

