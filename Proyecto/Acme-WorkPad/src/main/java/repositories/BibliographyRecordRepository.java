package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.BibliographyRecord;

@Repository
public interface BibliographyRecordRepository extends JpaRepository<BibliographyRecord, Integer>{

}
