
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.School;
import repositories.SchoolRepository;

@Transactional
@Service
public class SchoolService {

	@Autowired
	private SchoolRepository schoolRepository;


	public SchoolService() {
		super();
	}

	public <S extends School> S save(S entity) {
		return schoolRepository.save(entity);
	}

	public School findOne(Integer id) {
		return schoolRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		return schoolRepository.exists(id);
	}

	public void delete(Integer id) {
		schoolRepository.delete(id);
	}

	public List<School> findAll() {
		return schoolRepository.findAll();
	}

}
