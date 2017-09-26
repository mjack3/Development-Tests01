package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity{
	
	//Attributes
	
	private String folderName;
	private List<MailMessage> messages;
	
	//Getters
	
	@NotBlank
	public String getFolderName() {
		return folderName;
	}
	
	@OneToMany
	@NotNull
	public List<MailMessage> getMessages() {
		return messages;
	}
	
	//Setters
	
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	public void setMessages(List<MailMessage> messages) {
		this.messages = messages;
	}
	
	

}
