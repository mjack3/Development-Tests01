
package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "`group`")
public class Group extends DomainEntity {

	private String				name;
	private String				description;
	private Date				startDate;
	private Date				endDate;
	private List<Submission>	submission;
	private List<Student>		students;


	public Group() {
		super();
		this.submission = new ArrayList<Submission>();
		this.students = new ArrayList<Student>();
	}
	//Getters

	@NotNull
	@ManyToMany(mappedBy = "groups")
	public List<Student> getStudents() {
		return this.students;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	@Future
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	@NotNull
	@OneToMany
	public Collection<Submission> getSubmission() {
		return this.submission;
	}

	//Setters
	public void setName(final String name) {
		this.name = name;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setSubmission(final List<Submission> submission) {
		this.submission = submission;
	}

	public void setStudents(final List<Student> students) {
		this.students = students;
	}

}
