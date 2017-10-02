
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Teacher;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	/**
	 * 
	 * @param id
	 * @return the administrator logged
	 */
	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator getPrincipal(int id);

	/**
	 * 
	 * @param
	 * @return el profesor que ense�a m�s asignaturas.
	 */
	@Query("select t from Teacher t where t.subjects.size = (select max(c.subjects.size) from Teacher c)")
	List<Teacher> teacherMoreSubjects();

	/**
	 * 
	 * @param
	 * @return el profesor que ense�a menos asignaturas.
	 */
	@Query("select t from Teacher t where t.subjects.size = (select min(c.subjects.size) from Teacher c)")
	List<Teacher> teacherMinSubjects();

	/**
	 * 
	 * @param
	 * @return el profesor que ense�a el promedio de asignaturas m�s menos 10%.
	 */
	@Query("select t from Teacher t where t.subjects.size >= (select avg(c.subjects.size)*0.9 from Teacher c) or t.subjects.size <= (select avg(c.subjects.size)*1.1 from Teacher c)")
	List<Teacher> teacherAverageSubjects();
	//TODO

	/**
	 * 
	 * @param
	 * @return El m�nimo, el m�ximo, y el n�mero promedio de asignaturas impartidas por los profesores.
	 */
	@Query("select min(t.subjects.size), max(t.subjects.size), avg(t.subjects.size) from Teacher t")
	Object[] teacherMinMaxAvgSubjects();

	/**
	 * 
	 * @param
	 * @return El m�nimo, el m�ximo y el n�mero medio de plazas ofrecidas por asignatura.
	 */
	@Query("select min(s.seats), max(s.seats), avg(s.seats) from Subject s")
	Object[] MinMaxAvgSeatsOfSubjects();

	/**
	 * 
	 * @param
	 * @return El m�nimo, el m�ximo, y el n�mero promedio de alumnos que se han inscrito por asignatura.
	 */
	@Query("select min(s.students.size), max(s.students.size), avg(s.students.size) from Subject s")
	Object[] MinMaxAvgStudentsOfSubjects();

	/**
	 * 
	 * @param
	 * @return El m�nimo, el m�ximo y el promedio de las tareas por asignatura.
	 */
	@Query("select min(s.assigments.size), max(s.assigments.size), avg(s.assigments.size) from Subject s")
	Object[] MinMaxAvgAssigmentsOfSubjects();
	/**
	 * 
	 * @param
	 * @return Lista de todos los actores.
	 */

	@Query("select a.userAccount.username from Actor a")
	List<String> allActorName();

}
