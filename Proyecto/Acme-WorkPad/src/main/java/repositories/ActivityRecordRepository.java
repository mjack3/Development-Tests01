
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

	@Query("select resul from Actor a join a.activitiesRecords resul where a.userAccount.id = ?1 AND (resul.description NOT LIKE 'edits.%' AND resul.description NOT LIKE 'creates.%' AND resul.description NOT LIKE 'deletes.%' AND resul.description NOT LIKE 'posts.%' AND resul.description NOT LIKE 'enrols.%' AND resul.description NOT LIKE 'submits.%' AND resul.description NOT LIKE 'registers.%')")
	List<ActivityRecord> findAllPrincipal(int id);

	@Query("select resul from Actor a join a.activitiesRecords resul where resul.id=?1 and a.userAccount.id = ?2")
	ActivityRecord findOnePrincipal(int q, int id);

	@Query("select resul from Actor a join a.activitiesRecords resul where  a.userAccount.id = ?1 AND (resul.description NOT LIKE 'edits.%' AND resul.description NOT LIKE 'creates.%' AND resul.description NOT LIKE 'deletes.%' AND resul.description NOT LIKE 'posts.%' AND resul.description NOT LIKE 'enrols.%' AND resul.description NOT LIKE 'submits.%' AND resul.description NOT LIKE 'registers.%')")
	List<ActivityRecord> findAllByUserAccountId(int userAccountId);

	@Query("select resul from Actor a join a.activitiesRecords resul where a.userAccount.id = ?1 AND (resul.description LIKE 'edits.%' OR resul.description LIKE 'creates.%' OR resul.description LIKE 'deletes.%' OR resul.description LIKE 'posts.%' OR resul.description LIKE 'enrols.%' OR resul.description LIKE 'submits.%' OR resul.description LIKE 'registers.%')")
	Collection<ActivityRecord> findAllSystemByUserAccountId(int id);

}
