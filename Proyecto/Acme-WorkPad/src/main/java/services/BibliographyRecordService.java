package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.BibliographyRecordRepository;

@Transactional
@Service
public class BibliographyRecordService {
	
	@Autowired
	private BibliographyRecordRepository	repository;
	
	public BibliographyRecordService() {
		super();
	}

}
