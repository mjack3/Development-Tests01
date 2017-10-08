package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	private Student toSave;

	public StudentController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = createNewModelAndView(studentService.create(), null);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		result = createEditModelAndView(studentService.checkPrincipal(), null);

		return result;
	}

	@RequestMapping(value="/save", method=RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Student student, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(student, null);
		} else {
			try {
				Pattern pattern = Pattern.compile("^\\+([0-9][0-9]{0,2}) (\\([0-9][0-9][0-9]{0,3}\\)) ([a-zA-Z0-9 -]{4,})$");
			    Matcher matcher = pattern.matcher(student.getPhone());
				if(!matcher.matches()) {
					result = new ModelAndView("student/confirm");
					toSave = student;
				}else {
					studentService.save(student);
					result = new ModelAndView("redirect:/welcome/index.do");
				}			
			} catch (Throwable th) {
				th.printStackTrace();
				result = createNewModelAndView(student, "student.commit.error");
		}
		
	}
	return result;
}
	
	@RequestMapping(value="/saveEdit", method=RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid Student student, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(student, null);
		} else {
			try {
				Pattern pattern = Pattern.compile("^\\+([0-9][0-9]{0,2}) (\\([0-9][0-9][0-9]{0,3}\\)) ([a-zA-Z0-9 -]{4,})$");
			    Matcher matcher = pattern.matcher(student.getPhone());
				if(!matcher.matches()) {
					result = new ModelAndView("student/confirm");
					toSave = student;
				}else {
				studentService.update(student);
				result = new ModelAndView("redirect:/welcome/index.do");
				}
			} catch (Throwable th) {
				th.printStackTrace();
				result = createEditModelAndView(student, "student.commit.error");
		}
		
	}
	return result;
}
	
	@RequestMapping(value="/saveConfirm", method=RequestMethod.POST)
	public ModelAndView saveConfirm() {
		ModelAndView result;
			try {
				studentService.save(toSave);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable th) {
				th.printStackTrace();
				result = createNewModelAndView(toSave, "student.commit.error");
		}
		
	return result;
}
	
	@RequestMapping(value="/saveConfirmEdit", method=RequestMethod.POST)
	public ModelAndView saveConfirmEdit() {
		ModelAndView result;
			try {
				studentService.update(toSave);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable th) {
				th.printStackTrace();
				result = createNewModelAndView(toSave, "student.commit.error");
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

protected ModelAndView createEditModelAndView(Student student, String message) {
	ModelAndView result;
	result = new ModelAndView("student/edit");
	result.addObject("student", student);
	result.addObject("message", message);
	return result;
}








}
