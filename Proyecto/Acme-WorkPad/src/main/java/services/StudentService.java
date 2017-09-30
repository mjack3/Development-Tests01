package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Student;
import repositories.StudentRepository;
import security.LoginService;
import security.UserAccount;

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
	 * @param actor Estudiante a actualizar
	 * @return estudiante actualizado
	 */

	public Student update(Student actor) {

		Assert.notNull(actor);
		Assert.isTrue(repository.exists(actor.getId()));
		Assert.isTrue(actor.getPhone().matches("^$|^\\\\+([1-9][0-9]{0,2}) (\\\\([1-9][0-9]{0,3}\\\\)) ([a-zA-Z0-9 -]{4,})$"));

		return repository.save(actor);
	}

	/**
	 * Crea un nuevo estudiante
	 * @param actor Estudiante a crear
	 * @return estudiante creado
	 */

	public Student create(Student actor) {

		Assert.notNull(actor);
		Assert.isTrue(actor.getPhone().matches("^$|^\\\\+([1-9][0-9]{0,2}) (\\\\([1-9][0-9]{0,3}\\\\)) ([a-zA-Z0-9 -]{4,})$"));

		return repository.save(actor);
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


}
