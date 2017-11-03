
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SeminarService;
import services.StudentService;
import services.TeacherService;
import domain.Seminar;
import domain.Student;
import domain.Teacher;

@Controller
@RequestMapping("/seminar")
public class SeminarController extends AbstractController {

	@Autowired
	SeminarService	seminarService;

	@Autowired
	TeacherService	teacherService;

	@Autowired
	StudentService	studentService;


	@RequestMapping(value = "/teacher/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Seminar seminar, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("seminar/edit");
			result.addObject("seminar", seminar);
		} else
			try {
				final Teacher teacher = this.teacherService.checkPrincipal();
				seminar = this.seminarService.save(seminar);
				if (!teacher.getSeminars().contains(seminar)) {
					teacher.getSeminars().add(seminar);
					this.teacherService.update(teacher);
				}
				result = new ModelAndView("seminar/list");
				result.addObject("seminars", teacher.getSeminars());
			} catch (final Throwable th) {

				result = new ModelAndView("seminar/edit");
				result.addObject("seminar", seminar);
				result.addObject("message", "folder.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/teacher/edit", method = RequestMethod.GET)
	public ModelAndView teacherEdit(@RequestParam final Seminar q) {
		ModelAndView result;

		result = new ModelAndView("seminar/edit");
		result.addObject("seminar", q);

		return result;
	}

	@RequestMapping(value = "/teacher/delete", method = RequestMethod.GET)
	public ModelAndView teacherDelete(@RequestParam final Seminar q) {
		ModelAndView result;
		final Teacher teacher = this.teacherService.checkPrincipal();
		try {
			teacher.getSeminars().remove(q);
			this.teacherService.update(teacher);
			this.seminarService.delete(q);
			result = new ModelAndView("seminar/list");
			result.addObject("seminars", teacher.getSeminars());
		} catch (final Throwable th) {
			result = new ModelAndView("seminar/list");
			result.addObject("seminars", teacher.getSeminars());
		}

		return result;
	}

	@RequestMapping(value = "/teacher/create", method = RequestMethod.GET)
	public ModelAndView teacherCreate() {
		ModelAndView result;

		result = new ModelAndView("seminar/create");
		result.addObject("seminar", this.seminarService.create());

		return result;
	}

	@RequestMapping(value = "/teacher/list", method = RequestMethod.GET)
	public ModelAndView teacherList() {
		ModelAndView result;

		final Teacher teacher = this.teacherService.checkPrincipal();

		result = new ModelAndView("seminar/list");
		result.addObject("seminars", teacher.getSeminars());

		return result;
	}

	@RequestMapping(value = "/student/list", method = RequestMethod.GET)
	public ModelAndView studentList() {
		ModelAndView result;

		final Student student = this.studentService.checkPrincipal();
		result = new ModelAndView("seminar/list");
		result.addObject("seminars", this.seminarService.findAll());
		result.addObject("mySeminars", student.getSeminars());

		return result;
	}

	@RequestMapping(value = "/student/myList", method = RequestMethod.GET)
	public ModelAndView myStudentList() {
		ModelAndView result;

		final Student student = this.studentService.checkPrincipal();
		result = new ModelAndView("seminar/list");
		result.addObject("seminars", student.getSeminars());
		result.addObject("mySeminars", student.getSeminars());

		return result;
	}

	@RequestMapping(value = "/student/into", method = RequestMethod.GET)
	public ModelAndView goIn(@RequestParam final int q) {

		ModelAndView resul;

		try {

			this.studentService.goInSeminary(q);
			resul = this.myStudentList();

		} catch (final Throwable oops) {
			resul = this.myStudentList();
			resul.addObject("message", "seminar.commit.error");
		}
		return resul;

	}

	@RequestMapping(value = "/student/out", method = RequestMethod.GET)
	public ModelAndView goOut(@RequestParam final int q) {

		ModelAndView resul;

		try {

			this.studentService.goOutSeminary(q);
			resul = this.myStudentList();

		} catch (final Throwable oops) {
			resul = this.myStudentList();
			resul.addObject("message", "seminar.commit.error");
		}
		return resul;

	}

}
