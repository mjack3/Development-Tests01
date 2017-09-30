package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.FolderRepository;

@Transactional
@Service
public class FolderService {
	
	@Autowired
	private FolderRepository	repository;
	
	public FolderService() {
		super();
	}

}
