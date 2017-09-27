
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
	@Range(min=1)
	public Integer getTryNumber() {
		return tryNumber;
	}

	public Double getMark() {
		return mark;
	}

	@NotBlank
	public String getContent() {
		return content;
	}

	@NotNull
	@ElementCollection(targetClass = String.class)
	public List<String> getAttachments() {
		return attachments;
	}

	public Integer getGrade() {
		return grade;
	}

	//Setters
	public void setContent(String content) {
		this.content = content;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	public void setTryNumber(Integer tryNumber) {
		this.tryNumber = tryNumber;
	}

	public void setMark(Double mark) {
		this.mark = mark;
	}

}
