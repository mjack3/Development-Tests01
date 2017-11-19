
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	//Attributes

	private String				folderName;
	private Folder				folderFather;
	private List<Folder>		folderChildren;
	private List<MailMessage>	messages;


	//Getters

	@NotBlank
	public String getFolderName() {
		return this.folderName;
	}

	@OneToMany
	@NotNull
	public List<MailMessage> getMessages() {
		return this.messages;
	}

	//Setters

	public void setFolderName(final String folderName) {
		this.folderName = folderName;
	}

	public void setMessages(final List<MailMessage> messages) {
		this.messages = messages;
	}

	@OneToMany
	public List<Folder> getFolderChildren() {
		return this.folderChildren;
	}

	public void setFolderChildren(final List<Folder> folderChildren) {
		this.folderChildren = folderChildren;
	}

	@ManyToOne(targetEntity = Folder.class)
	public Folder getFolderFather() {
		return this.folderFather;
	}

	public void setFolderFather(final Folder folderFather) {
		this.folderFather = folderFather;
	}

}
