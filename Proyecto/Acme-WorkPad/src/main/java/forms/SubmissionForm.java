
package forms;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class SubmissionForm {

	private int				groupId;
	private int				assignmentId;
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

	public Integer getGrade() {
		return this.grade;
	}

	//Setters
	public void setContent(final String content) {
		this.content = content;
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

	public int getAssignmentId() {
		return this.assignmentId;
	}

	public void setAssignmentId(final int assignmentId) {
		this.assignmentId = assignmentId;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(final int groupId) {
		this.groupId = groupId;
	}

	@ElementCollection
	public List<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final List<String> attachments) {
		this.attachments = attachments;
	}

}
