
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.ActivityRecord;
import domain.Administrator;
import domain.SocialIdentity;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Transactional
@Service
public class AdministratorService {

	@Autowired
	private AdministratorRepository	repository;

	@Autowired
	private FolderService			folderService;


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

	public Administrator create() {

		final Administrator administrator = new Administrator();
		administrator.setName(new String());
		administrator.setSurname(new String());
		administrator.setEmail(new String());
		administrator.setPhone(new String());
		administrator.setPostalAddress(new String());
		administrator.setSocialIdentities(new ArrayList<SocialIdentity>());
		administrator.setActivitiesRecords(new ArrayList<ActivityRecord>());
		administrator.setFolders(this.folderService.save(this.folderService.createDefaultFolders()));

		final Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		administrator.setUserAccount(account);

		return administrator;

	}

	public Administrator save(final Administrator actor) {

		Assert.notNull(actor);

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

	public boolean exists(final Integer id) {
		Assert.notNull(id);

		return this.repository.exists(id);
	}

	public List<Administrator> findAll() {
		return this.repository.findAll();
	}

	public List<Object> getDashboard() {
		final List<Object> res = new ArrayList<Object>();

		res.add(this.repository.teacherMoreSubjects());
		res.add(this.repository.teacherMinSubjects());
		res.add(this.repository.teacherAverageSubjects());
		res.add(this.repository.teacherMinMaxAvgSubjects());
		res.add(this.repository.minMaxAvgSeatsOfSubjects());
		res.add(this.repository.minMaxAvgStudentsOfSubjects());
		res.add(this.repository.minMaxAvgAssigmentsOfSubjects());
		res.add(this.repository.minMaxAvgActivityRecordOfActor());
		res.add(this.repository.avgActivityRecordOfActor());
		res.add(this.repository.minMaxAvgSeminarsOTeacher());
		res.add(this.repository.avgSeminarsOTeacher());

		return res;
	}

	public List<String> allActorName() {
		return this.repository.allActorName();
	}

	public Administrator findOne(final Integer idAdmin) {
		// TODO Auto-generated method stub
		Assert.notNull(idAdmin);
		Assert.isTrue((this.repository.exists(idAdmin)));

		return this.repository.findOne(idAdmin);
	}


	@Autowired
	LoginService loginService;


	public void editInfo(final Administrator admin) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.hasRole("ADMINISTRATOR"));
		Assert.isTrue(LoginService.getPrincipal().getId() == admin.getUserAccount().getId());

		this.save(admin);

	}

}
