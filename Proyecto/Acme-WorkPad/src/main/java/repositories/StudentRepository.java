package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
	
	/**
	 * 
	 * @param id
	 * @return the student logged
	 */
	@Query("select s from Student s where s.userAccount.id = ?1")
	Student getPrincipal(int id);

}
