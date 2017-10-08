
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity {

	private String	nick;
	private String	socialName;
	private String	link;
	private String	photo;


	//Getter
	@NotBlank
	public String getNick() {
		return nick;
	}

	@NotBlank
	public String getSocialName() {
		return socialName;
	}

	@URL
	public String getLink() {
		return link;
	}

	@NotNull
	public String getPhoto() {
		return photo;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
