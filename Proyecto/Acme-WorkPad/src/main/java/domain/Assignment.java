
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Assignment extends DomainEntity {

	private String				title;
	private String				description;
	private String				link;
	private Date				startDate;
	private Date				endDate;
	private List<Submission>	submission;


	//Getters
	@NotBlank
	public String getTitle() {
		return title;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	@URL
	public String getLink() {
		return link;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	@NotNull
	@OneToMany
	public List<Submission> getSubmission() {
		return submission;
	}

	//Setters
	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setSubmission(List<Submission> submission) {
		this.submission = submission;
	}

}
