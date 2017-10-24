
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Student extends Actor {

	private List<Seminar>	seminars;
	private List<Group>		groups;
	private List<Subject>	subjects;


	//Getters
	@NotNull
	@ManyToMany
	public List<Seminar> getSeminars() {
		return this.seminars;
	}

	@NotNull
	@ManyToMany
	public List<Group> getGroups() {
		return this.groups;
	}

	@NotNull
	@ManyToMany
	public List<Subject> getSubjects() {
		return this.subjects;
	}

	//Setters
	public void setSeminars(final List<Seminar> seminars) {
		this.seminars = seminars;
	}

	public void setGroups(final List<Group> groups) {
		this.groups = groups;
	}

	public void setSubjects(final List<Subject> subjects) {
		this.subjects = subjects;
	}

}
