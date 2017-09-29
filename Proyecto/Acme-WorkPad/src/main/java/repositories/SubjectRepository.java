
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

	/**
	 * 
	 * @param word
	 * @return all instance without seats that contains word
	 */

	@Query("select s from Subject s where s.title LIKE '%?1%' and s.seats <= s.students.size")
	Collection<Subject> findSubjectsByWordWithoutSeats(String word);

	/**
	 * 
	 * @param word
	 * @return all instance with seats that contains word
	 */

	@Query("select s from Subject s where s.title LIKE '%?1%' and s.seats > s.students.size ")
	Collection<Subject> findSubjectsByWordWithSeats(String word);

	@Query("select s.administrator from Subject s where s.id=?1")
	Administrator findAdministratorRegisterSubject(Integer a);

	/**
	 * Devuelve la asignatura asociada al profesor y actividad
	 * 
	 * @param id
	 * @param id2
	 * @return
	 */
	@Query("select ts from Teacher t join t.subjects ts join ts.activities tsa where t.id = ?1 and tsa.id=?2")
	Subject findSubjectByTeacherIdActivityId(int id, int id2);

}
