
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Category;
import repositories.CategoryRepository;

@Transactional
@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;


	public CategoryService() {
		super();
	}

	public List<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final Integer categoryId) {
		Assert.notNull(categoryId);
		return this.categoryRepository.findOne(categoryId);
	}

}
