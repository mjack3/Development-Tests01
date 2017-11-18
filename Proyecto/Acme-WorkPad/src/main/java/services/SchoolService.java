
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SchoolRepository;
import domain.School;

@Transactional
@Service
public class SchoolService {

	@Autowired
	private SchoolRepository	schoolRepository;


	public SchoolService() {
		super();
	}

	public School save(final School entity) {
		return this.schoolRepository.save(entity);
	}

	public School findOne(final Integer id) {
		return this.schoolRepository.findOne(id);
	}

	public boolean exists(final Integer id) {
		return this.schoolRepository.exists(id);
	}

	public void delete(final Integer id) {
		this.schoolRepository.delete(id);
	}

	public List<School> findAll() {
		return this.schoolRepository.findAll();
	}

	public String findBannerSchool() {
		final String image = new String(this.schoolRepository.findAll().get(0).getBanner());
		return image;
	}

}
