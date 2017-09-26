
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class BibliographyRecord extends DomainEntity {

	private String	title;
	private String	authors;
	private String	locator;


	//Getters
	@NotBlank
	public String getTitle() {
		return title;
	}

	@NotBlank
	public String getAuthors() {
		return authors;
	}

	@NotBlank
	@Column(unique=true)
	public String getLocator() {
		return locator;
	}

	//Setters
	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

}
