
package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
public class ActivityForm extends DomainEntity {

	private String	title;
	private String	description;
	private String	link;
	private Date	startDate;
	private Date	endDate;
	private Integer	subjectId;


	//Getters
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@URL
	public String getLink() {
		return this.link;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Future
	public Date getEndDate() {
		return this.endDate;
	}

	//Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotNull
	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(final Integer subjectId) {
		this.subjectId = subjectId;
	}

}
