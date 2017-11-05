
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ActivityRecord;

@Repository
public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Integer> {

	@Query("select resul from Actor a join a.activitiesRecords resul where a.id = ?1")
	Collection<ActivityRecord> display(Integer idActor);

	@Query("select resul from Actor a join a.activitiesRecords resul where a.userAccount.id = ?1")
	List<ActivityRecord> findAllPrincipal(int id);

	@Query("select resul from Actor a join a.activitiesRecords resul where resul.id=?1 and a.userAccount.id = ?2")
	ActivityRecord findOnePrincipal(int q, int id);

}
