package se.hitract.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.hitract.model.domains.MAIL_TYPE;

@Getter
@Setter
@NoArgsConstructor // Essential for "new SentMail()" and JPA
@Entity
public class SendMail extends CommonEntity {

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

	@Override
	public Long getId() {
		return sendMailId;
	}
}
