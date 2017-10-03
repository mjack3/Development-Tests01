
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return categoryRepository.findAll();
	}

}
