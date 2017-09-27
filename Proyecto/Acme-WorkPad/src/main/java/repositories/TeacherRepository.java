
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;
import domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

	/**
	 * 
	 * @param id
	 * @return the teacher logged
	 */
	@Query("select t from Teacher t where t.userAccount.id = ?1")
	Teacher getPrincipal(int id);

	/**
	 * Devuelve las actividades de una asignatura impartida por un profesor
	 * 
	 * @param subjectId
	 * @param teacherId
	 * @return Collection<Activity>
	 */

	@Query("select ts.activities from Teacher t join t.subjects ts where t.id = ?2 and ts.id = ?1")
	Collection<Activity> findAllActivitiesBySubject(int subjectId, int teacherId);

}
