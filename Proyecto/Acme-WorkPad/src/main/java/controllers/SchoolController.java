
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import domain.School;
import security.LoginService;
import services.SchoolService;

@Controller
@RequestMapping("/school")
public class SchoolController {

	@Autowired
	SchoolService	schoolService;

	@Autowired
	LoginService	loginService;


	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		result = new ModelAndView("school/edit");
		final Administrator d = (Administrator) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (d.getSchools().contains(schoolService.findAll().iterator().next())) {
			result.addObject("school", schoolService.findAll().iterator().next());
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid School school, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(school, null);
		} else {
			try {
				final Administrator d = (Administrator) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
				if (d.getSchools().contains(schoolService.findAll().iterator().next())) {
					schoolService.save(school);
				} else {
					result = new ModelAndView("redirect:/welcome/index.do");
				}

				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable th) {
				result = createEditModelAndView(school, "school.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(School school, String message) {
		ModelAndView result = new ModelAndView("school/edit");

		result.addObject("school", school);
		result.addObject("message", message);

		return result;
	}

}
