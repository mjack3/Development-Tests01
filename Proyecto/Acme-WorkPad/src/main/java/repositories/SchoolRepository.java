
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

}
