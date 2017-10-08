package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Seminar;
import domain.Student;
import domain.Teacher;
import services.SeminarService;
import services.StudentService;
import services.TeacherService;

@Controller
@RequestMapping("/seminar")
public class SeminarController extends AbstractController {

	@Autowired
	SeminarService seminarService;
	
	@Autowired
	TeacherService teacherService;
	
	@Autowired
	StudentService studentService;
	
	
	@RequestMapping(value="/teacher/save", method=RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Seminar seminar, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("seminar/edit");
			result.addObject("seminar", seminar);
		} else {
			try {
				Teacher teacher = teacherService.checkPrincipal();
				seminar = seminarService.save(seminar);
				if(!teacher.getSeminars().contains(seminar)) {
					teacher.getSeminars().add(seminar);
					teacherService.update(teacher);
				}
				result = new ModelAndView("seminar/list");
				result.addObject("seminars", teacher.getSeminars());
			} catch (Throwable th) {
				
				result = new ModelAndView("seminar/edit");
				result.addObject("seminar", seminar);
				result.addObject("message", "folder.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/teacher/edit", method = RequestMethod.GET)
	public ModelAndView teacherEdit(@RequestParam Seminar q) {
		ModelAndView result;

		result = new ModelAndView("seminar/edit");
		result.addObject("seminar", q);

		return result;
	}
	
	@RequestMapping(value = "/teacher/delete", method = RequestMethod.GET)
	public ModelAndView teacherDelete(@RequestParam Seminar q) {
		ModelAndView result;
		Teacher teacher = teacherService.checkPrincipal();
		try {
			teacher.getSeminars().remove(q);
			teacherService.update(teacher);
			seminarService.delete(q);
			result = new ModelAndView("seminar/list");
			result.addObject("seminars", teacher.getSeminars());
		} catch (Throwable th) {		
			result = new ModelAndView("seminar/list");
			result.addObject("seminars", teacher.getSeminars());
		}

		return result;
	}
	
	@RequestMapping(value = "/teacher/create", method = RequestMethod.GET)
	public ModelAndView teacherCreate() {
		ModelAndView result;

		result = new ModelAndView("seminar/create");
		result.addObject("seminar", seminarService.create());

		return result;
	}
	
	@RequestMapping(value = "/teacher/list", method = RequestMethod.GET)
	public ModelAndView teacherList() {
		ModelAndView result;
		
		Teacher teacher = teacherService.checkPrincipal();

		result = new ModelAndView("seminar/list");
		result.addObject("seminars", teacher.getSeminars());

		return result;
	}
	
	@RequestMapping(value = "/student/list", method = RequestMethod.GET)
	public ModelAndView studentList() {
		ModelAndView result;
		
		Student student = studentService.checkPrincipal();
		result = new ModelAndView("seminar/list");
		result.addObject("seminars", seminarService.findAll());
		result.addObject("mySeminars", student.getSeminars());

		return result;
	}
	
	@RequestMapping(value = "/student/myList", method = RequestMethod.GET)
	public ModelAndView myStudentList() {
		ModelAndView result;
		
		Student student = studentService.checkPrincipal();
		result = new ModelAndView("seminar/list");
		result.addObject("seminars", student.getSeminars());
		result.addObject("mySeminars", student.getSeminars());

		return result;
	}
}
