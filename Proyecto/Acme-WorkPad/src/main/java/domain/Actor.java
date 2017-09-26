
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	private String					name;
	private String					surname;
	private String					email;
	private String					phone;
	private String					postalAddress;
	private UserAccount				userAccount;
	private List<Folder>			folders;
	private List<ActivityRecord>	activitiesRecords;
	private List<SocialIdentity>	socialIdentities;


	//Getters
	@NotBlank
	public String getName() {
		return name;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	@Email
	public String getEmail() {
		return email;
	}

	@NotNull
	public String getPhone() {
		return phone;
	}

	@NotNull
	public String getPostalAddress() {
		return postalAddress;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	@NotNull
	@OneToMany
	public List<Folder> getFolders() {
		return folders;
	}

	@NotNull
	@OneToMany
	public List<ActivityRecord> getActivitiesRecords() {
		return activitiesRecords;
	}

	@NotNull
	@OneToMany
	public List<SocialIdentity> getSocialIdentities() {
		return socialIdentities;
	}

	//Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public void setFolders(List<Folder> folders) {
		this.folders = folders;
	}

	public void setActivitiesRecords(List<ActivityRecord> activitiesRecords) {
		this.activitiesRecords = activitiesRecords;
	}

	public void setSocialIdentities(List<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

}
