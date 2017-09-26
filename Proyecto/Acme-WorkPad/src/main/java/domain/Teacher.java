
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
		return bibliographiesRecords;
	}

	@NotNull
	@OneToMany
	public List<Seminar> getSeminars() {
		return seminars;
	}

	@NotNull
	@OneToMany
	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setBibliographiesRecords(List<BibliographyRecord> bibliographiesRecords) {
		this.bibliographiesRecords = bibliographiesRecords;
	}

	public void setSeminars(List<Seminar> seminars) {
		this.seminars = seminars;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
