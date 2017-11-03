
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class ActivityRecord extends DomainEntity {

	private String				description;
	private Date				writtenDate;
	private Collection<String>	attachment;


	public ActivityRecord() {
		super();
		this.attachment = new HashSet<String>();
	}

	//Getters

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getWrittenDate() {
		return this.writtenDate;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getAttachment() {
		return this.attachment;
	}

	//Setters

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setWrittenDate(final Date writtenDate) {
		this.writtenDate = writtenDate;
	}

	public void setAttachment(final Collection<String> attachment) {
		this.attachment = attachment;
	}

}
