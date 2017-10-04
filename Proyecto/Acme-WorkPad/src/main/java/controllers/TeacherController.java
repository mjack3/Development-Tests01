
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Teacher;
import services.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;


	public TeacherController() {
		super();
	}

	@RequestMapping("/view")
	public ModelAndView view(@RequestParam final Teacher q) {
		ModelAndView res;

		res = new ModelAndView("teacher/view");
		res.addObject("teacher", q);

		return res;
	}

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = this.createNewModelAndView(this.teacherService.create(), null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		result = this.createEditModelAndView(this.teacherService.checkPrincipal(), null);

		return result;
	}

	@RequestMapping(value = "/administrator/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final Teacher teacher, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createNewModelAndView(teacher, null);
		else
			try {
				this.teacherService.save(teacher);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createNewModelAndView(teacher, "student.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Teacher teacher, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(teacher, null);
		else
			try {
				this.teacherService.update(teacher);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndView(teacher, "student.commit.error");
			}
		return result;
	}

	protected ModelAndView createNewModelAndView(final Teacher teacher, final String message) {
		ModelAndView result;
		result = new ModelAndView("teacher/create");
		result.addObject("teacher", teacher);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Teacher teacher, final String message) {
		ModelAndView result;
		result = new ModelAndView("teacher/edit");
		result.addObject("teacher", teacher);
		result.addObject("message", message);
		return result;
	}

}
