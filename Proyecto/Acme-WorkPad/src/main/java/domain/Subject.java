
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Subject extends DomainEntity {

	private String						title;
	private String						ticker;
	private String						syllabus;
	private Integer						seats;
	private List<Bulletin>				bulletins;
	private List<Activity>				activities;
	private List<Assignment>			assigments;
	private List<Group>					groups;
	private Teacher						teacher;
	private List<BibliographyRecord>	bibliographiesRecords;
	private Category					category;
	private Administrator				administrator;
	private List<Student>				students;


	//Getters
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@Pattern(regexp = "^\\w{2}-\\d{5}$")
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	@NotBlank
	public String getSyllabus() {
		return this.syllabus;
	}

	@Range(min = 0)
	public Integer getSeats() {
		return this.seats;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	@NotNull
	@OneToMany
	public List<BibliographyRecord> getBibliographiesRecords() {
		return this.bibliographiesRecords;
	}

	@NotNull
	@OneToMany
	public List<Bulletin> getBulletins() {
		return this.bulletins;
	}

	@NotNull
	@OneToMany
	public List<Activity> getActivities() {
		return this.activities;
	}

	@NotNull
	@OneToMany
	public List<Assignment> getAssigments() {
		return this.assigments;
	}

	@NotNull
	@OneToMany
	public List<Group> getGroups() {
		return this.groups;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Administrator getAdministrator() {
		return this.administrator;
	}

	@NotNull
	@ManyToMany
	public List<Student> getStudents() {
		return this.students;
	}

	//Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setSyllabus(final String syllabus) {
		this.syllabus = syllabus;
	}

	public void setSeats(final Integer seats) {
		this.seats = seats;
	}

	public void setBulletins(final List<Bulletin> bulletins) {
		this.bulletins = bulletins;
	}

	public void setActivities(final List<Activity> activities) {
		this.activities = activities;
	}

	public void setAssigments(final List<Assignment> assigments) {
		this.assigments = assigments;
	}

	public void setGroups(final List<Group> groups) {
		this.groups = groups;
	}

	public void setTeacher(final Teacher teacher) {
		this.teacher = teacher;
	}

	public void setBibliographiesRecords(final List<BibliographyRecord> bibliographiesRecords) {
		this.bibliographiesRecords = bibliographiesRecords;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}

	public void setStudents(final List<Student> students) {
		this.students = students;
	}

}
