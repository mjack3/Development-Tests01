
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
	@Query("select g from Subject s join s.groups g where s.id=?1 and g.id = (Select g1.id from Student st join st.groups g1 where st.id=?2)")
	Group findGroupBySubjectAndStudent(int idSubject, int idStudent);

}
