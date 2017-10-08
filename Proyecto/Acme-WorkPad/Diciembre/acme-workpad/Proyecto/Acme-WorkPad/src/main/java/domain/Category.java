
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
public class Category extends DomainEntity {

	private Category		categoryFather;
	private List<Category>	categoryChildren;
	private String 			name;


	//Getters
	@ManyToOne(targetEntity=Category.class)
	public Category getCategoryFather() {
		return categoryFather;
	}

	@NotNull
	@OneToMany
	public List<Category> getCategoryChildren() {
		return categoryChildren;
	}
	
	@NotBlank
	public String getName() {
		return name;
	}


	//Setters
	public void setCategoryFather(Category categoryFather) {
		this.categoryFather = categoryFather;
	}

	public void setCategoryChildren(List<Category> categoryChildren) {
		this.categoryChildren = categoryChildren;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
