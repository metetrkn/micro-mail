package se.hitract.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContactInfoMailDTO {
    private String name;
    private String email;
    private String phone;
    private String subject;
    private String content;
    private List<AttachmentsDTO> attachments;
}
