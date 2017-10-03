
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

	/**
	 * Devuelve todas las instancias asociadas a un profesor y a una asignatura
	 * 
	 * @param id
	 * @param subjectId
	 * @return
	 */
	@Query("select resul from Teacher t join t.subjects s join s.assigments resul where t.id=?1 AND s.id = ?2")
	Collection<Assignment> findAllTeacherIdSubjectId(int id, int subjectId);

	/**
	 * Devuelve el assignent del profesor logueado
	 * 
	 * @param id
	 * @param q
	 * @return
	 */
	@Query("select resul from Teacher t join t.subjects s join s.assigments resul where t.id = ?1 AND resul.id = ?2")
	Assignment findOneTeacherIdAssignmentId(int id, int q);

}
