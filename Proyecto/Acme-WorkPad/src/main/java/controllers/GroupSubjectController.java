
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.GroupSubject;
import services.GroupSubjectService;

@Controller
@RequestMapping("/groupsubject")
public class GroupSubjectController {

	@Autowired
	GroupSubjectService groupsubjectService;


	@RequestMapping(value = "/student/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = createNewModelAndView(groupsubjectService.create(), null);

		return result;
	}

	@RequestMapping(value = "/student/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid GroupSubject groupsubject, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			for (ObjectError e : binding.getAllErrors()) {
				System.out.println(e);
			}
			result = createNewModelAndView(groupsubject, null);
		} else {
			try {
				groupsubjectService.save(groupsubject);
				result = new ModelAndView("redirect:/groupsubject/list.do");
			} catch (Throwable th) {
				th.printStackTrace();
				result = createNewModelAndView(groupsubject, "groupsubject.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(GroupSubject groupsubject, String message) {
		ModelAndView result;
		result = new ModelAndView("groupsubject/create");
		result.addObject("groupsubject", groupsubject);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/student/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("groupsubject/list");
		result.addObject("groupsubject", groupsubjectService.findAll());

		return result;
	}

}
