package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BulletinRepository;
import domain.Bulletin;
import domain.Subject;
import forms.BulletinForm;

@Service
@Transactional
public class BulletinService {

	// Managed repository ------------------------------------------
	@Autowired
	private BulletinRepository bulletinRepository;

	// Supporting Services ------------------------------------------
	@Autowired(required = false)
	private Validator validator;

	@Autowired
	private SubjectService subjectService;

	// Constructors -------------------------------------------------
	public BulletinService() {
		super();
	}

	// CRUD methods -------------------------------------------------
	public Bulletin create() {
		Bulletin result;

		result = new Bulletin();

		Assert.notNull(result);

		return result;
	}

	public void delete(Bulletin entity) {
		Assert.notNull(entity);
		bulletinRepository.delete(entity);
	}

	public Bulletin findOne(final Integer bulletinId) {
		Bulletin result;

		Assert.notNull(bulletinId);
		result = this.bulletinRepository.findOne(bulletinId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Bulletin> findAll() {
		Collection<Bulletin> result;

		result = this.bulletinRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Bulletin save(final Bulletin bulletin) {

		Bulletin result = this.bulletinRepository.save(bulletin);

		return result;
	}

	public Bulletin saveInSubject(final Bulletin bulletin, int subjectId) {
		Assert.notNull(bulletin);
		Assert.notNull(subjectId);
		Subject subject = subjectService.findOne(subjectId);
		Assert.notNull(subject);
		
		Bulletin result = this.bulletinRepository.save(bulletin);
		subject.getBulletins().add(result);
		subjectService.save(subject);
		return result;
	}

	public Bulletin reconstruct(BulletinForm bulletinForm, BindingResult binding) {
		
		Bulletin bulletin = this.create();
		bulletin.setPostedDate(bulletinForm.getPostedDate());
		bulletin.setText(bulletinForm.getText());
		bulletin.setTitle(bulletinForm.getTitle());
		
		this.validator.validate(bulletin, binding);
		
		return bulletin;
	}

	public Collection<Bulletin> findBySubject(Integer q) {
		return this.bulletinRepository.findBySubject(q);

	}

}
