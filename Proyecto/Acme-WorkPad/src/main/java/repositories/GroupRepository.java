
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

	@Query("select g from Subject s join s.groups g where s.id=?1 and g.id = (Select g1.id from Student st join st.groups g1 where st.id=?2)")
	Group findGroupBySubjectAndStudent(int idSubject, int idStudent);

	@Query("select e.groups from Group a join a.students b join b.subjects e where b.id=?1")
	List<Group> studentByGroups(int q);

	@Query("select resul from Student s join s.groups resul where s.id != ?2 and resul.id = ?1")
	Group findOneNoJoinPrincipal(Integer idGroup, Integer idStudent);

}
