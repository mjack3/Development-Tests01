
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

	private List<Seminar>		seminars;
	private List<GroupSubject>	groups;
	private List<Subject>		subjects;


	//Getters
	@NotNull
	@ManyToMany
	public List<Seminar> getSeminars() {
		return seminars;
	}

	@NotNull
	@ManyToMany
	public List<GroupSubject> getGroups() {
		return groups;
	}

	@NotNull
	@ManyToMany
	public List<Subject> getSubjects() {
		return subjects;
	}

	//Setters
	public void setSeminars(List<Seminar> seminars) {
		this.seminars = seminars;
	}

	public void setGroups(List<GroupSubject> groups) {
		this.groups = groups;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
