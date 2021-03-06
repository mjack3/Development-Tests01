
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	private List<School> 	schools;
	private List<Subject>	subjects;

	//Getters
	
	@NotNull
	@OneToMany
	public List<Subject> getSubjects() {
		return subjects;
	}

	@NotNull
	@OneToMany
	public List<School> getSchools() {
		return schools;
	}

	//Setters

	public void setSchools(List<School> schools) {
		this.schools = schools;
	}
	

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
