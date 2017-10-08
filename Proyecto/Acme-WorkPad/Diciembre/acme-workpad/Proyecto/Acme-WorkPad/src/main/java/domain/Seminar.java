
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
		return title;
	}

	@NotBlank
	public String getSummary() {
		return summary;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getOrganisedDate() {
		return organisedDate;
	}

	@Range(min = 0)
	public Integer getDuration() {
		return duration;
	}

	@NotBlank
	public String getHall() {
		return hall;
	}

	@Range(min = 1)
	public Integer getSeats() {
		return seats;
	}

	//Setters

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setOrganisedDate(Date organisedDate) {
		this.organisedDate = organisedDate;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public void setHall(String hall) {
		this.hall = hall;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

}
