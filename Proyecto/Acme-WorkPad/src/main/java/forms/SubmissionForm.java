
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;


public class SubmissionForm {
	private int groupId;
	private int assignmentId;
	private String			content;
	private String	attachments;
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

	
	public String getAttachments() {
		return attachments;
	}

	public Integer getGrade() {
		return grade;
	}

	//Setters
	public void setContent(String content) {
		this.content = content;
	}

	public void setAttachments(String attachments) {
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

	public int getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

}
