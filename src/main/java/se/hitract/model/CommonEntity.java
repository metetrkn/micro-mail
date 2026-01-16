package se.hitract.model;

import jakarta.persistence.*;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import se.hitract.model.enums.EntityType;
import se.hitract.service.mail.dto.CommonDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class CommonEntity implements java.io.Serializable {

	public abstract Long getId();

	@GenericField(sortable = Sortable.YES, name = "created_sort")
	protected Date created;

	@GenericField(sortable = Sortable.YES, name = "updated_sort")
	protected Date updated;

	@Transient
	private String type;

	@PrePersist
	protected void onCreate() {
		if(created == null) {
			created = new Date();
		}
		if(updated == null) {
			updated = new Date();
		}
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date();
	}

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

	@Override
	public String toString() {
		return "[ " + getType() + "(" + getId() + ")" + getAttributesData() + " ]";
	}

	//Override in subclasses
	@Transient
	protected String getAttributesData() {
		return "";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
		if(o instanceof CommonDTO) {
			throw new RuntimeException("Do not compare DTO with model");
		}
        if (!(o instanceof CommonEntity)) return false;
        CommonEntity ent = (CommonEntity) o;
        return getId() != null && Objects.equals(getId(), ent.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Transient
    public EntityType getTargetEntityType() {
    	return null;
    }
}

