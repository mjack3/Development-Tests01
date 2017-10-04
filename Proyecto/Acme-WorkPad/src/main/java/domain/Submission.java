
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Submission extends DomainEntity {

	private String			content;
	private List<String>	attachments;
	private Integer			grade;
	private Integer			tryNumber;
	private Double			mark;


	//Getters

	@NotNull
	@Range(min = 1)
	public Integer getTryNumber() {
		return this.tryNumber;
	}

	public Double getMark() {
		return this.mark;
	}

	@NotBlank
	public String getContent() {
		return this.content;
	}

	@NotNull
	@ElementCollection(targetClass = String.class)
	public List<String> getAttachments() {
		return this.attachments;
	}

	@Range(min = 0, max = 100)
	public Integer getGrade() {
		return this.grade;
	}

	//Setters
	public void setContent(final String content) {
		this.content = content;
	}

	public void setAttachments(final List<String> attachments) {
		this.attachments = attachments;
	}

	public void setGrade(final Integer grade) {
		this.grade = grade;
	}

	public void setTryNumber(final Integer tryNumber) {
		this.tryNumber = tryNumber;
	}

	public void setMark(final Double mark) {
		this.mark = mark;
	}

}
