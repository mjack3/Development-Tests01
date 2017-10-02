
package repositories;

import java.util.Collection;
import java.util.List;

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

	@Query("select s from Subject s where s.title like concat('%', ?1, '%') and s.seats <= s.students.size")
	Collection<Subject> findSubjectsByWordWithoutSeats(String word);

	/**
	 * 
	 * @param word
	 * @return all instance with seats that contains word
	 */
	
	@Query("select s from Subject s where s.title like concat('%', ?1, '%') and s.seats > s.students.size")
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

	/**
	 * Seleciona asignaturas por estudiante
	 * 
	 * @param id
	 * @return
	 */

	@Query("select a.subjects from Student a where a.id=?1")
	List<Subject> subjectsByStudents(int id);

	@Query("select s from Subject s where s.teacher.id = ?1")
	Collection<Subject> findAllByPrincipal(int id);

	@Query("select distinct s from Subject s join s.students students where s.title like concat('%', ?1, '%') and s.seats <= s.students.size and (s.administrator.userAccount.id=?2 or s.teacher.userAccount.id=?2 or students.userAccount.id=?2)")
	Collection<Subject> findSubjectsByWordWithoutSeats(String keyword, int id);

	@Query("select distinct s from Subject s join s.students students where s.title like concat('%', ?1, '%') and s.seats > s.students.size and (s.administrator.userAccount.id=?2 or s.teacher.userAccount.id=?2 or students.userAccount.id=?2)")
	Collection<Subject> findSubjectsByWordWithSeats(String keyword, int id);

}
