package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Student;
import services.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService	studentService;

	private Boolean confirmado = false;


	public StudentController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = createNewModelAndView(studentService.create(), null);

		return result;
	}

	@RequestMapping(value="/save", method=RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Student student, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(student, null);
		} else {
			try {

				studentService.save(student);
				result = new ModelAndView("redirect:/welcome/index.do");
				confirmado = false;
			} catch (Throwable th) {
				th.printStackTrace();
				result = createNewModelAndView(student, "student.commit.error");
		}
		
	}
	return result;
}

protected ModelAndView createNewModelAndView(Student student, String message) {
	ModelAndView result;
	result = new ModelAndView("student/create");
	result.addObject("student", student);
	result.addObject("message", message);
	return result;
}








}
