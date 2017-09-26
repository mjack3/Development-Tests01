package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class MailMessage extends DomainEntity{
	
	//Attributes
	
	private Date sent;
	private String subject;
	private String body;
	private Priority priority;
	private Actor sender;
	private Actor recipient;
	
	//Getters
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	public Date getSent() {
		return sent;
	}
	
	@NotBlank
	public String getSubject() {
		return subject;
	}
	
	@NotBlank
	public String getBody() {
		return body;
	}
	
	@Valid
	@NotNull
	public Priority getPriority() {
		return priority;
	}
	
	@ManyToOne(optional=false)
	@NotNull
	public Actor getSender() {
		return sender;
	}
	
	@ManyToOne(optional=false)
	@NotNull
	public Actor getRecipient() {
		return recipient;
	}
	
	//Setters
	
	public void setSent(Date sent) {
		this.sent = sent;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public void setSender(Actor sender) {
		this.sender = sender;
	}
	
	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}
	
	

}
