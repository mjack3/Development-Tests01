
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Assignment;
import domain.Submission;

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
	
	@Query("select a from Student s join s.subjects su join su.assigments a where s.id=?1")
	Collection<Assignment> findAllByPrincipalStudent(int id);
	
	@Query("select a from Student s join s.groups g join s.subjects su join su.assigments a where s.id=?1 and g in su.groups")
	Collection<Assignment> findAllByPrincipal(int id);
	
	@Query("select c.submission from Group c where c.id=?1")
	Collection<Submission> findAllByGroupId(int id);

}
