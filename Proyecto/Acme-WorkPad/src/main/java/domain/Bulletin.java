
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Bulletin extends DomainEntity {

	private String	title;
	private String	text;
	private Date	postedDate;


	//Getters

	@NotBlank
	public String getTitle() {
		return title;
	}

	@NotBlank
	public String getText() {
		return text;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	public Date getPostedDate() {
		return postedDate;
	}

	//Setters
	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

}
