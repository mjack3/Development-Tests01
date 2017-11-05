
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	public ActorService() {
		super();
	}


	@Autowired
	private ActorRepository	actorRepository;
	@Autowired
	private LoginService	loginService;


	public Actor findOnePrincipal() {
		// TODO Auto-generated method stub+
		Assert.isTrue(LoginService.isAnyAuthenticated());

		final Actor a = this.actorRepository.findByPrincipal(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		return a;
	}

	public void update(final Actor a) {
		// TODO Auto-generated method stub
		Assert.isTrue(this.actorRepository.exists(a.getId()));
		this.actorRepository.save(a);
	}

}
