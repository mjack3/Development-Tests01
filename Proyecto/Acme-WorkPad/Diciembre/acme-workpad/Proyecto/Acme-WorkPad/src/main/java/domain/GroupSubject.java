
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class GroupSubject extends DomainEntity {

	private String				name;
	private String				description;
	private Date				startDate;
	private Date				endDate;
	private List<Submission>	submission;
	private List<Student>		students;


	//Getters
	@NotBlank
	public String getName() {
		return name;
	}

	@NotBlank
	public String getDescription() {
		return description;
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

	@NotNull
	@ManyToMany
	public List<Student> getStudents() {
		return students;
	}

	//Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
