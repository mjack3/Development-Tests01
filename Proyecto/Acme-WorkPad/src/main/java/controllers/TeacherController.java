package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Teacher;
import services.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherService	teacherService;


	public TeacherController() {
		super();
	}

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = createNewModelAndView(teacherService.create(), null);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		result = createEditModelAndView(teacherService.checkPrincipal(), null);

		return result;
	}

	@RequestMapping(value="/administrator/save", method=RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Teacher teacher, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(teacher, null);
		} else {
			try {
				teacherService.save(teacher);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable th) {
				th.printStackTrace();
				result = createNewModelAndView(teacher, "student.commit.error");
		}
		
	}
	return result;
}
	@RequestMapping(value="/saveEdit", method=RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid Teacher teacher, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(teacher, null);
		} else {
			try {
				teacherService.save(teacher);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable th) {
				th.printStackTrace();
				result = createEditModelAndView(teacher, "student.commit.error");
		}
		
	}
	return result;
}

protected ModelAndView createNewModelAndView(Teacher teacher, String message) {
	ModelAndView result;
	result = new ModelAndView("teacher/create");
	result.addObject("teacher", teacher);
	result.addObject("message", message);
	return result;
}

protected ModelAndView createEditModelAndView(Teacher teacher, String message) {
	ModelAndView result;
	result = new ModelAndView("teacher/edit");
	result.addObject("teacher", teacher);
	result.addObject("message", message);
	return result;
}








}
