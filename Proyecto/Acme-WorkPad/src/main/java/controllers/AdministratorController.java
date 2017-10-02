
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import domain.Subject;
import security.LoginService;
import services.AdministratorService;
import services.SubjectService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	SubjectService			subjectService;

	@Autowired
	LoginService			loginService;


	//Mis asignaturas

	@RequestMapping(value = "/subject/list", method = RequestMethod.GET)
	public ModelAndView listAdministrator() {
		ModelAndView result;

		final Administrator a = (Administrator) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		result = new ModelAndView("subject/list");
		result.addObject("requestURI", "/administrator/subject/list.do");
		final List<Subject> subjectByAdministrator = new ArrayList<Subject>();
		if (this.administratorService.exists(a.getId()))
			for (final Subject sub : a.getSubjects())
				subjectByAdministrator.add(sub);

		result.addObject("subject", subjectByAdministrator);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/subject/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer q) {
		ModelAndView result;
		result = new ModelAndView("subject/edit");
		result.addObject("subject", subjectService.findOne(q));
		return result;
	}
	
	//Dashboard
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("administrator/dashboard");
		result.addObject("dashboard", administratorService.getDashboard());


		return result;
	}

	/*
	 * @RequestMapping(value = "/subject/edit", method = RequestMethod.POST, params = "save")
	 * public ModelAndView save(@Valid final Announcement announcement, final BindingResult binding) {
	 * ModelAndView result;
	 *
	 * if (binding.hasErrors())
	 * result = this.createEditModelAndView(announcement);
	 * else
	 * try {
	 * this.announcementService.save(announcement);
	 * result = new ModelAndView("redirect:list.do");
	 * } catch (final Throwable oops) {
	 * result = this.createEditModelAndView(announcement, "announcement.commit.error");
	 * }
	 *
	 * return result;
	 * }
	 */

	/*
	 * @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	 * public ModelAndView delete(final Announcement announcement, final BindingResult binding) {
	 * ModelAndView result;
	 *
	 * try {
	 * this.announcementService.delete(announcement);
	 * result = new ModelAndView("redirect:list.do");
	 * } catch (final Throwable oops) {
	 * result = this.createEditModelAndView(announcement, "announcement.commit.error");
	 * }
	 *
	 * return result;
	 * }
	 */

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Subject subject) {
		ModelAndView result;

		result = this.createEditModelAndView(subject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Subject subject, final String message) {
		ModelAndView result;

		result = new ModelAndView("subject/edit");

		result.addObject("subject", subject);
		result.addObject("message", message);

		return result;
	}

}
