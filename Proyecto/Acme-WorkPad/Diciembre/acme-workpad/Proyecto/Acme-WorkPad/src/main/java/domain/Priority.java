package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Priority {

	//Attributes
	
	private final String HIGH="HIGH",NEUTRAL="NEUTRAL",LOW="LOW";
	private String value;
	
	//Getters
	
	@NotBlank
	@Pattern(regexp = "^" + HIGH + "|" + NEUTRAL + "|" + LOW + "$")
	public String getValue() {
		return value;
	}
	
	
	//Setters
	
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
