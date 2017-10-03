
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
	
	@Query("select s from Assignment a join a.submission s where a.id=?1 and s.id=(select s1.id from Group g join g.submission s1 where g.id=?2)")
	Collection<Submission> findSubmissionsByGroupAndAssignment(
			int assignmentId, int id);

}
