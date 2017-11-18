
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

	@Override
	@Query("select a from School a")
	List<School> findAll();

}
