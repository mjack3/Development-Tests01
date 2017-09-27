
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

	/**
	 * 
	 * @param word
	 * @return all instance that contain a word in title or description
	 */

	@Query("select s from Subject s where s.title LIKE %?1% OR s.description LIKE %?1%")
	Collection<Subject> findSubjectWord(String word);

}
