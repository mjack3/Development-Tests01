
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Seminar extends DomainEntity {

	private String	title;
	private String	summary;
	private Date	organisedDate;
	private Integer	duration;
	private String	hall;
	private Integer	seats;


	//Getters
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getOrganisedDate() {
		return this.organisedDate;
	}

	@Range(min = 1)
	@NotNull
	public Integer getDuration() {
		return this.duration;
	}

	@NotBlank
	public String getHall() {
		return this.hall;
	}

	@Range(min = 0)
	@NotNull
	public Integer getSeats() {
		return this.seats;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public void setOrganisedDate(final Date organisedDate) {
		this.organisedDate = organisedDate;
	}

	public void setDuration(final Integer duration) {
		this.duration = duration;
	}

	public void setHall(final String hall) {
		this.hall = hall;
	}

	public void setSeats(final Integer seats) {
		this.seats = seats;
	}

}
