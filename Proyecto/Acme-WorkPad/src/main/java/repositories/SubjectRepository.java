
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

	/**
	 * 
	 * @param word
	 * @return all instance without seats that contains word
	 */

	@Query("select s from Subject s where s.name LIKE '%?1%' and s.seats <= s.students.size")
	Collection<Subject> findSubjectsByWordWithoutSeats(String word);

	/**
	 * 
	 * @param word
	 * @return all instance with seats that contains word
	 */

	@Query("select s from Subject s where s.name LIKE '%?1%' and s.seats > s.students.size ")
	Collection<Subject> findSubjectsByWordWithSeats(String word);

}
