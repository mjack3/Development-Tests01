
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

	private String						name;
	private String						ticker;
	private String						syllabus;
	private Integer						seats;
	private List<Bulletin>				bulletins;
	private List<Activity>				activities;
	private List<Assignment>			assigments;
	private List<GroupSubject>			groups;
	private Teacher						teacher;
	private List<BibliographyRecord>	bibliographiesRecords;
	private Category					category;
	private Administrator				administrator;
	private List<Student>				students;


	//Getters
	@NotBlank
	public String getName() {
		return name;
	}

	@Pattern(regexp = "^\\w{2}-\\d{5}$")
	@Column(unique = true)
	public String getTicker() {
		return ticker;
	}

	@NotBlank
	public String getSyllabus() {
		return syllabus;
	}

	@Range(min = 1)
	public Integer getSeats() {
		return seats;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Teacher getTeacher() {
		return teacher;
	}

	@NotNull
	@OneToMany
	public List<BibliographyRecord> getBibliographiesRecords() {
		return bibliographiesRecords;
	}

	@NotNull
	@OneToMany
	public List<Bulletin> getBulletins() {
		return bulletins;
	}

	@NotNull
	@OneToMany
	public List<Activity> getActivities() {
		return activities;
	}

	@NotNull
	@OneToMany
	public List<Assignment> getAssigments() {
		return assigments;
	}

	@NotNull
	@OneToMany
	public List<GroupSubject> getGroups() {
		return groups;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Category getCategory() {
		return category;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Administrator getAdministator() {
		return administrator;
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

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public void setBulletins(List<Bulletin> bulletins) {
		this.bulletins = bulletins;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void setAssigments(List<Assignment> assigments) {
		this.assigments = assigments;
	}

	public void setGroups(List<GroupSubject> groups) {
		this.groups = groups;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setBibliographiesRecords(List<BibliographyRecord> bibliographiesRecords) {
		this.bibliographiesRecords = bibliographiesRecords;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setAdministator(Administrator administator) {
		this.administrator = administator;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
