
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	/**
	 * Devuelve una actividad asociada a un profesor
	 * 
	 * @param id
	 * @param q
	 * @return
	 */
	@Query("select activities from Subject s join s.activities activities where activities.id =?2 and s.teacher.id = ?1")
	Activity findOneByTeacherIdActivityId(int id, int q);
}
