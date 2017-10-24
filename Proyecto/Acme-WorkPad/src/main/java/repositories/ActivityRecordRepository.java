
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ActivityRecord;

@Repository
public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Integer> {

	@Query("select resul from Actor a join a.activitiesRecords resul where a.id = ?1")
	Collection<ActivityRecord> display(Integer idActor);

	@Query("select resul from Actor a join a.activitiesRecords resul where a.userAccount.id = ?1")
	Collection<ActivityRecord> findAllPrincipal(int id);

}
