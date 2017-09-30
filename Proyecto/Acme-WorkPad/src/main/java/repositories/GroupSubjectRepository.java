
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.GroupSubject;

@Repository
public interface GroupSubjectRepository extends JpaRepository<GroupSubject, Integer> {

}
