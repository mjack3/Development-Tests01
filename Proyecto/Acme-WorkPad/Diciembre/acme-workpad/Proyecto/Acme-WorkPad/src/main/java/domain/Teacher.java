
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Teacher extends Actor {

	private List<BibliographyRecord>	bibliographiesRecords;
	private List<Seminar>				seminars;
	private List<Subject>				subjects;


	//Getters
	@NotNull
	@OneToMany
	public List<BibliographyRecord> getBibliographiesRecords() {
		return this.bibliographiesRecords;
	}

	@NotNull
	@OneToMany
	public List<Seminar> getSeminars() {
		return this.seminars;
	}

	@NotNull
	@OneToMany(mappedBy = "teacher")
	public List<Subject> getSubjects() {
		return this.subjects;
	}

	public void setBibliographiesRecords(final List<BibliographyRecord> bibliographiesRecords) {
		this.bibliographiesRecords = bibliographiesRecords;
	}

	public void setSeminars(final List<Seminar> seminars) {
		this.seminars = seminars;
	}

	public void setSubjects(final List<Subject> subjects) {
		this.subjects = subjects;
	}

}
