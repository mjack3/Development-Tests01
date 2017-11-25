
package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
	private Collection<String>	attachments;


	public ActivityRecord() {
		super();
		this.attachments = new ArrayList<String>();

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
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	//Setters

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setWrittenDate(final Date writtenDate) {
		this.writtenDate = writtenDate;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

}
