package se.hitract.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import se.hitract.model.Attachment;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
//@EntityListeners(ContactInfoListener.class)
public class ContactInfo implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contactInfoId;

	private String name;
	
	private String company;
	
	private String email;

	private String subject;

	@Lob
	private String content;

	private String phone;

	private List<Attachment> attachments = new ArrayList<Attachment>();

}
