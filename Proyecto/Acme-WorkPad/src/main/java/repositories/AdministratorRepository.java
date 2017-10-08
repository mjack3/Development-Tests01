
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
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
	 * @return el profesor que enseña más asignaturas.
	 */
	@Query("select t from Teacher t where t.subjects.size = (select max(c.subjects.size) from Teacher c)")
	List<Teacher> teacherMoreSubjects();

	/**
	 * 
	 * @param
	 * @return el profesor que enseña menos asignaturas.
	 */
	@Query("select t from Teacher t where t.subjects.size = (select min(c.subjects.size) from Teacher c)")
	List<Teacher> teacherMinSubjects();

	/**
	 * 
	 * @param
	 * @return el profesor que enseña el promedio de asignaturas más menos 10%.
	 */
	@Query("select t from Teacher t where t.subjects.size >= (select avg(c.subjects.size)*0.9 from Teacher c) or t.subjects.size <= (select avg(c.subjects.size)*1.1 from Teacher c)")
	List<Teacher> teacherAverageSubjects();
	//TODO

	/**
	 * 
	 * @param
	 * @return El mínimo, el máximo, y el número promedio de asignaturas impartidas por los profesores.
	 */
	@Query("select min(t.subjects.size), max(t.subjects.size), avg(t.subjects.size) from Teacher t")
	Object[] teacherMinMaxAvgSubjects();

	/**
	 * 
	 * @param
	 * @return El mínimo, el máximo y el número medio de plazas ofrecidas por asignatura.
	 */
	@Query("select min(s.seats), max(s.seats), avg(s.seats) from Subject s")
	Object[] minMaxAvgSeatsOfSubjects();

	/**
	 * 
	 * @param
	 * @return El mínimo, el máximo, y el número promedio de alumnos que se han inscrito por asignatura.
	 */
	@Query("select min(s.students.size), max(s.students.size), avg(s.students.size) from Subject s")
	Object[] minMaxAvgStudentsOfSubjects();

	/**
	 * 
	 * @param
	 * @return El mínimo, el máximo y el promedio de las tareas por asignatura.
	 */
	@Query("select min(s.assigments.size), max(s.assigments.size), avg(s.assigments.size) from Subject s")
	Object[] minMaxAvgAssigmentsOfSubjects();
	
	/**
	 * 
	 * @param
	 * @return El mínimo, el máximo, y el número promedio de los registros de actividad por actor
	 */
	@Query("select min(a.activitiesRecords.size), max(a.activitiesRecords.size), avg(a.activitiesRecords.size) from Actor a")
	Object[] minMaxAvgActivityRecordOfActor();
	
	/**
	 * 
	 * @param
	 * @return Los actores que tienen ± 10% del número promedio de registros de actividad por el actor
	 */
	@Query("select a from Actor a where a.activitiesRecords.size >= (select avg(b.activitiesRecords.size)*0.9 from Actor b) or a.activitiesRecords.size <= (select avg(c.activitiesRecords.size)*1.1 from Actor c)")
	List<Actor> avgActivityRecordOfActor();
	
	/**
	 * 
	 * @param
	 * @return El mínimo, el máximo y el número medio de seminarios por maestro
	 */
	@Query("select min(t.seminars.size), max(t.seminars.size), avg(t.seminars.size) from Teacher t")
	Object[] minMaxAvgSeminarsOTeacher();
	
	/**
	 * 
	 * @param
	 * @return Los maestros que organizan ± 10% el número medio de seminarios por maestro
	 */
	@Query("select t from Teacher t where t.seminars.size >= (select avg(b.seminars.size)*0.9 from Actor b) or t.seminars.size <= (select avg(c.seminars.size)*1.1 from Actor c)")
	List<Actor> avgSeminarsOTeacher();
	/**
	 * 
	 * @param
	 * @return Lista de todos los actores.
	 */

	@Query("select a.userAccount.username from Actor a")
	List<String> allActorName();

}
