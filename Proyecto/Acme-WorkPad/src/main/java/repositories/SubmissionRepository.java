
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

	@Query("select distinct(s) from Assignment a join a.submission s where a.id=?1 and s in (select s1 from Group g join g.submission s1 where g.id=?2))")
	Collection<Submission> findSubmissionsByGroupAndAssignment(int assignmentId, int id);

	/**
	 * Devuelve submission asocaido a un profesor
	 * 
	 * @param id
	 * @param submissionId
	 * @return
	 */
	@Query("select resul from Teacher t join t.subjects s join s.assigments ass join ass.submission resul where t.id = ?1 AND resul.id = ?2")
	Submission findOneTeacherIdSubmissionId(int id, int submissionId);

}
