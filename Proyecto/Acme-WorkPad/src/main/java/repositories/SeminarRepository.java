
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Seminar;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Integer> {

	@Query("select resul from Teacher t join t.seminars resul where t.id = ?1")
	Collection<Seminar> findAllIdTeacher(int id);

}
