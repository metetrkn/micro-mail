package se.hitract.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import se.hitract.model.enums.EntityType;
import se.hitract.model.domains.MAIL_TYPE;
import se.hitract.model.enums.SENT_MAIL_STATUS;

@Getter
@Setter
@NoArgsConstructor // Essential for "new SentMail()" and JPA
@Entity
public class SentMail extends CommonEntity {

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

	private Long studentId;

	@NotNull
	@Enumerated(EnumType.STRING)
	private MAIL_TYPE mailType;

	@Column(columnDefinition = "TEXT")
	private String error;

	@Override
	public Long getId() {
		return sentMailId;
	}
}