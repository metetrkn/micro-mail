package se.hitract.model;

import jakarta.persistence.*;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import se.hitract.model.enums.EntityType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class CommonEntity implements java.io.Serializable {

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

	//Override in subclasses
	@Transient
	protected String getAttributesData() {
		return "";
	}


    @Override
    public int hashCode() {
        return 31;
    }

    @Transient
    public EntityType getTargetEntityType() {
    	return null;
    }

	public abstract Long getId();
}

