
package domain;

import java.util.Date;
import java.util.List;

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

	private String			description;
	private Date			writtenDate;
	private List<String>	attachment;


	//Getters

	@NotBlank
	public String getDescription() {
		return description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	public Date getWrittenDate() {
		return writtenDate;
	}

	@NotNull
	@ElementCollection(targetClass = String.class)
	public List<String> getAttachment() {
		return attachment;
	}

	//Setters

	public void setDescription(String description) {
		this.description = description;
	}

	public void setWrittenDate(Date writtenDate) {
		this.writtenDate = writtenDate;
	}

	public void setAttachment(List<String> attachment) {
		this.attachment = attachment;
	}

}
