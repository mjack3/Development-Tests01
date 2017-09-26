
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class School extends DomainEntity {

	private String			name;
	private String			banner;
	private List<Subject>	subjects;


	//Getters
	@NotBlank
	public String getName() {
		return name;
	}

	@URL
	public String getBanner() {
		return banner;
	}

	@NotNull
	@OneToMany
	public List<Subject> getSubjects() {
		return subjects;
	}


	//Setters

	public void setName(String name) {
		this.name = name;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}


}
