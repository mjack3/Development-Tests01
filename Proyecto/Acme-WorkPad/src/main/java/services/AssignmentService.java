
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AssignmentRepository;
import domain.Assignment;

@Service
@Transactional
public class AssignmentService {

	@Autowired
	private AssignmentRepository	repository;
	@Autowired
	private TeacherService			teacherService;


	public AssignmentService() {
		super();
	}

	// Simple CRUDs methods ------------------------------

	/**
	 * Guarda en la BD un assignment
	 * 
	 * @param assignment
	 * @return Assignement
	 */

	public Assignment save(final Assignment assignment) {
		Assert.notNull(assignment);

		final Assignment saved = this.repository.save(assignment);

		return saved;
	}

	public Assignment update(final Assignment assignment) {
		// TODO Auto-generated method stub
		Assert.isTrue(this.repository.exists(assignment.getId()));
		final Assignment saved = this.repository.save(assignment);
		return saved;
	}

	public void delete(final Assignment assignment) {
		// TODO Auto-generated method stub
		Assert.notNull(assignment);
		Assert.isTrue(this.repository.exists(assignment.getId()));
		this.teacherService.checkPrincipal();
		this.repository.delete(assignment);
	}
}
