package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Seminar;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Integer>{

}
