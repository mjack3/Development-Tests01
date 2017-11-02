
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
	@NotNull
	public String getName() {
		return this.name;
	}

	@NotBlank
	@NotNull
	public String getSurname() {
		return this.surname;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	@NotNull
	public String getPhone() {
		return this.phone;
	}

	@NotNull
	public String getPostalAddress() {
		return this.postalAddress;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	@NotNull
	@OneToMany
	public List<Folder> getFolders() {
		return this.folders;
	}

	@NotNull
	@OneToMany
	public List<ActivityRecord> getActivitiesRecords() {
		return this.activitiesRecords;
	}

	@NotNull
	@OneToMany
	public List<SocialIdentity> getSocialIdentities() {
		return this.socialIdentities;
	}

	//Setters
	public void setName(final String name) {
		this.name = name;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setPostalAddress(final String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public void setFolders(final List<Folder> folders) {
		this.folders = folders;
	}

	public void setActivitiesRecords(final List<ActivityRecord> activitiesRecords) {
		this.activitiesRecords = activitiesRecords;
	}

	public void setSocialIdentities(final List<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

}
