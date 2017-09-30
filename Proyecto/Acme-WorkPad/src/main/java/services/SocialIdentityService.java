package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SocialIdentityRepository;

@Transactional
@Service
public class SocialIdentityService {
	
	@Autowired
	private SocialIdentityRepository	repository;
	
	public SocialIdentityService() {
		super();
	}


}
