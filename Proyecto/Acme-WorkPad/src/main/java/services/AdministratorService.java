
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Administrator;
import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;

@Transactional
@Service
public class AdministratorService {

	@Autowired
	private AdministratorRepository repository;


	public AdministratorService() {
		super();
	}

	//CRUD Methods

	/**
	 * Actualiza un administrador existente
	 * 
	 * @param actor
	 *            administrador a actualizar
	 * @return administrador actualizado
	 */

	public Administrator update(final Administrator actor) {

		Assert.notNull(actor);
		Assert.isTrue(this.repository.exists(actor.getId()));
		Assert.isTrue(actor.getPhone().matches("^$|^\\\\+([1-9][0-9]{0,2}) (\\\\([1-9][0-9]{0,3}\\\\)) ([a-zA-Z0-9 -]{4,})$"));

		return this.repository.save(actor);
	}

	/**
	 * Crea un nuevo administrador
	 * 
	 * @param actor
	 *            administrador a crear
	 * @return administrador creado
	 */

	public Administrator create(final Administrator actor) {

		Assert.notNull(actor);
		Assert.isTrue(actor.getPhone().matches("^$|^\\\\+([1-9][0-9]{0,2}) (\\\\([1-9][0-9]{0,3}\\\\)) ([a-zA-Z0-9 -]{4,})$"));

		return this.repository.save(actor);
	}

	//Other Methods

	/**
	 * Comprueba que el logueado es administrador
	 *
	 * @return administrador logueado
	 */

	public Administrator checkPrincipal() {

		final UserAccount account = LoginService.getPrincipal();
		Assert.isTrue(account.getAuthorities().iterator().next().getAuthority().equals("ADMINISTRATOR"));

		return this.repository.getPrincipal(account.getId());
	}
}
