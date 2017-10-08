package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Seminar;
import repositories.SeminarRepository;

@Transactional
@Service
public class SeminarService {
	
	@Autowired
	private SeminarRepository	repository;
	

	public SeminarService() {
		super();
	}

	public void delete(Seminar entity) {
		Assert.notNull(entity);
		Assert.isTrue(repository.exists(entity.getId()));
		repository.delete(entity);
	}

	public Seminar create() {
		Seminar seminar = new Seminar();
		
		seminar.setDuration(0);
		seminar.setHall("");
		seminar.setOrganisedDate(new Date());
		seminar.setSeats(0);
		seminar.setSummary("");
		seminar.setTitle("");
		
		return seminar;
	}

	public Seminar save(Seminar entity) {
		Assert.notNull(entity);
		
		return repository.save(entity);
	}
	
	public Seminar update(Seminar entity) {
		Assert.notNull(entity);
		Assert.notNull(entity.getId());
		Assert.isTrue(repository.exists(entity.getId()));
		
		return repository.save(entity);
	}


	public List<Seminar> findAll() {
		return repository.findAll();
	}


	public Seminar findOne(Integer id) {
		Assert.notNull(id);
		return repository.findOne(id);
	}

	

}
