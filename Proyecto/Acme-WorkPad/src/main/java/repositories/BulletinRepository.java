
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bulletin;

@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Integer> {
	
	@Query("select b from Subject s join s.bulletins b where s.id=?1")
	Collection<Bulletin> findBySubject(Integer q);

}
