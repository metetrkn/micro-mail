package se.hitract.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import se.hitract.model.enums.MAIL_TYPE;

@SuppressWarnings("serial")
@Entity
public class SendMail extends  CommonEntity implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sendMailId;

	@NotNull
	private boolean mailSent;

	@NotNull
	private String email;

	@NotNull
	@Enumerated(EnumType.STRING)
	private MAIL_TYPE mailType;

	public Long getSendMailId() {
		return sendMailId;
	}

	public void setSendMailId(Long sendMailId) {
		this.sendMailId = sendMailId;
	}

	public boolean isMailSent() {
		return mailSent;
	}

	public void setMailSent(boolean mailSent) {
		this.mailSent = mailSent;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MAIL_TYPE getMailType() {
		return mailType;
	}

	public void setMailType(MAIL_TYPE mailType) {
		this.mailType = mailType;
	}

	@Override
	public Long getId() {
		return sendMailId;
	}
}
