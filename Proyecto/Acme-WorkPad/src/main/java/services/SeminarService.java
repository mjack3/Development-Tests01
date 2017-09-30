package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SeminarRepository;

@Transactional
@Service
public class SeminarService {
	
	@Autowired
	private SeminarRepository	repository;
	

	public SeminarService() {
		super();
	}

}
