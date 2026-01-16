package se.hitract.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import se.hitract.model.enums.EntityType;
import se.hitract.model.enums.MAIL_TYPE;
import se.hitract.model.enums.SENT_MAIL_STATUS;


@SuppressWarnings("serial")
@Entity
public class SentMail extends CommonEntity implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sentMailId;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private SENT_MAIL_STATUS sentMailStatus;

	private Long entityId;

	@Enumerated(EnumType.STRING)
	private EntityType entityType;
	
	@NotNull
	private String email;

	private Long toStudent;

	
	@NotNull
	@Enumerated(EnumType.STRING)
	private MAIL_TYPE mailType;
	
	@Column(columnDefinition = "TEXT")
	private String error;

	public SentMail() {
	}
	
	public SentMail(@NotNull SENT_MAIL_STATUS sentMailStatus , @NotNull String email, Long toStudent, MAIL_TYPE mailType, String error, EntityType entityType, Long entityId) {
		this.sentMailStatus = sentMailStatus;
		this.email = email;
		this.toStudent = toStudent;
		this.mailType = mailType;
		this.error = error;
		this.entityType = entityType;
		this.entityId = entityId;
	}

	@Override
	public Long getId() {
		return sentMailId;
	}

	public Long getSentMailId() {
		return sentMailId;
	}

	public void setSentMailId(Long sentMailId) {
		this.sentMailId = sentMailId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getToStudent() {
		return toStudent;
	}

	public void setToStudent(Long toStudent) {
		this.toStudent = toStudent;
	}

	public MAIL_TYPE getMailType() {
		return mailType;
	}

	public void setMailType(MAIL_TYPE mailType) {
		this.mailType = mailType;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public SENT_MAIL_STATUS getSentMailStatus() {
		return sentMailStatus;
	}

	public void setSentMailStatus(SENT_MAIL_STATUS sentMailStatus) {
		this.sentMailStatus = sentMailStatus;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
}
