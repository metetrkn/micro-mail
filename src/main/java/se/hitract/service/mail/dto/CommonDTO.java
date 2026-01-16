package se.hitract.service.mail.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import se.hitract.model.CommonEntity;
import se.hitract.model.enums.LANGUAGE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public abstract class CommonDTO {

	protected Date created;

	private Date updated;

	@JsonIgnore
	private LANGUAGE language;

	private String type;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getCreatedFormatted(String pattern) {
		return new SimpleDateFormat(pattern).format(this.getCreated());
	}

	public String getType() {
		return getClass().getSimpleName();
	}
	
	public abstract Long getId();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if(o instanceof CommonEntity) {
			throw new RuntimeException("Do not compare Entity with DTO");
		}
		if (!(o instanceof CommonDTO)) return false;
		CommonDTO dto = (CommonDTO) o;
		return getId() != null && Objects.equals(getId(), dto.getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	public LANGUAGE getLanguage() {
		return language;
	}

	public void setLanguage(LANGUAGE language) {
		this.language = language;
	}
}