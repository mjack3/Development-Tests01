
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Student;
import domain.Subject;
import security.LoginService;
import services.AdministratorService;
import services.StudentService;
import services.SubjectService;
import services.TeacherService;

@Controller
@RequestMapping("/subject")
public class SubjectController extends AbstractController {

	@Autowired
	SubjectService					subjectService;

	@Autowired
	LoginService					loginService;

	@Autowired
	StudentService					studentService;
	@Autowired
	private TeacherService			teacherService;
	@Autowired
	private AdministratorService	administratorService;


	public SubjectController() {
		super();
	}

	// Listing ---------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(defaultValue = "") final String keyword, final String sw) {
		ModelAndView view;

		Collection<Subject> subject;

		view = new ModelAndView("subject/list");

		if (sw.equals("sin"))
			subject = this.subjectService.findSubjectsByWordWithoutSeats(keyword);
		else
			subject = this.subjectService.findSubjectsByWordWithSeats(keyword);

		if (LoginService.hasRole("TEACHER"))
			view.addObject("principal", this.teacherService.checkPrincipal());
		view.addObject("subject", subject);
		view.addObject("requestURI", "subject/search.do");

		return view;
	}

	@RequestMapping(value = "/authenticated/search", method = RequestMethod.GET)
	public ModelAndView search2(@RequestParam(defaultValue = "") final String keyword, final String sw) {
		ModelAndView view;

		Collection<Subject> subject;

		view = new ModelAndView("subject/list");

		if (sw.equals("sin"))
			subject = this.subjectService.findSubjectsByWordWithoutSeatsByPrincipal(keyword);
		else
			subject = this.subjectService.findSubjectsByWordWithSeatsByPrincipal(keyword);

		view.addObject("subject", subject);
		view.addObject("requestURI", "subject/authenticated/search.do");
		if (LoginService.hasRole("TEACHER"))
			view.addObject("principal", this.teacherService.checkPrincipal());

		return view;
	}

	/*
	 * 
	 * 
	 * KARLI
	 */

	//Lista de asignatura por estudiante
	@RequestMapping(value = "/student/list", method = RequestMethod.GET)
	public ModelAndView listSubjectByStudent() {
		ModelAndView result;

		result = new ModelAndView("subject/list");
		result.addObject("requestURI", "/subject/student/list.do");
		final Student d = (Student) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		result.addObject("subject", this.subjectService.subjectsByStudents(d.getId()));
		if (this.studentService.exists(d.getId())) {
			final List<Subject> subjectByStudent = new ArrayList<Subject>();
			for (final Subject sub : d.getSubjects())
				subjectByStudent.add(sub);
			result.addObject("subjectByStudent", subjectByStudent);
		}

		result.addObject("requestSearch", "subject/authenticated/search.do");
		return result;
	}

	//Lista general
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("subject/list");
		result.addObject("requestURI", "/subject/list.do");
		result.addObject("subject", this.subjectService.findAll());
		result.addObject("requestSearch", "subject/search.do");

		if (LoginService.hasRole("TEACHER")) {
			result.addObject("principal", this.teacherService.checkPrincipal());
		}
		//Cambio karli para quedar una sola lista
		if (LoginService.hasRole("STUDENT")) {
			final Student d = (Student) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
			if (this.studentService.exists(d.getId())) {
				final List<Subject> subjectByStudent = new ArrayList<Subject>();
				for (final Subject sub : d.getSubjects())
					subjectByStudent.add(sub);
				result.addObject("subjectByStudent", subjectByStudent);
			}
		}

		return result;
	}
	//Para Suscribirse
	@RequestMapping(value = "/student/subscribe", method = RequestMethod.GET)
	public ModelAndView subscribe(@RequestParam final Subject q) {
		ModelAndView result;

		final Student student = (Student) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if ((!student.getSubjects().contains(q)) && q.getSeats() > 0) {
			q.setSeats(q.getSeats() - 1);
			this.subjectService.save(q);
			final List<Subject> subjects = student.getSubjects();
			subjects.add(q);
			student.setSubjects(subjects);

			this.studentService.save(student);

		}
		result = new ModelAndView("redirect:/subject/student/list.do");
		return result;
	}

}
