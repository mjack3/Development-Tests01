
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


	//Getters
	@NotBlank
	public String getContent() {
		return content;
	}

	@NotNull
	@ElementCollection(targetClass = String.class)
	public List<String> getAttachments() {
		return attachments;
	}

	@Range(min = 0, max = 100)
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

}
