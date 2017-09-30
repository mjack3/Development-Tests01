
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StudentRepository;
import security.LoginService;
import security.UserAccount;
import domain.Student;

@Transactional
@Service
public class StudentService {

	@Autowired
	private StudentRepository	repository;


	public StudentService() {
		super();
	}

	//CRUD Methods

	/**
	 * Actualiza un estudiante existente
	 * 
	 * @param actor
	 *            Estudiante a actualizar
	 * @return estudiante actualizado
	 */

	public Student update(final Student actor) {

		Assert.notNull(actor);
		Assert.isTrue(this.repository.exists(actor.getId()));
		Assert.isTrue(actor.getPhone().matches("^$|^\\\\+([1-9][0-9]{0,2}) (\\\\([1-9][0-9]{0,3}\\\\)) ([a-zA-Z0-9 -]{4,})$"));

		return this.repository.save(actor);
	}

	/**
	 * Crea un nuevo estudiante
	 * 
	 * @param actor
	 *            Estudiante a crear
	 * @return estudiante creado
	 */

	public Student create(final Student actor) {

		Assert.notNull(actor);
		Assert.isTrue(actor.getPhone().matches("^$|^\\\\+([1-9][0-9]{0,2}) (\\\\([1-9][0-9]{0,3}\\\\)) ([a-zA-Z0-9 -]{4,})$"));

		return this.repository.save(actor);
	}

	//Other Methods

	/**
	 * Comprueba que el logueado es profesor
	 * 
	 * @return profesor logueado
	 */

	public Student checkPrincipal() {
		// TODO Auto-generated method stub
		final UserAccount account = LoginService.getPrincipal();
		Assert.isTrue(account.getAuthorities().iterator().next().getAuthority().equals("STUDENT"));

		return this.repository.getPrincipal(account.getId());
	}

	public Student save(final Student entity) {
		Assert.notNull(entity);

		return this.repository.save(entity);
	}

	public boolean exists(final int id) {
		// TODO Auto-generated method stub
		Assert.notNull(id);
		return this.repository.exists(id);
	}

}
