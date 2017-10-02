
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
		result.addObject("requestSearch", "subject/authenticated/search.do");
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/subject/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer q) {
		ModelAndView result;
		result = new ModelAndView("subject/edit");
		result.addObject("subject", this.subjectService.findOne(q));
		return result;
	}

	@RequestMapping(value = "/subject/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final HttpServletRequest request, @Valid final Subject subject, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(subject, null);
		else
			try {
				this.subjectService.save(subject);
				result = new ModelAndView("redirect:/subject/candidate/list.do");
			} catch (final Throwable th) {
				result = this.createEditModelAndView(subject, "subject.commit.error");
			}
		return result;
	}

	/*
	 * @RequestMapping(value="/candidate/edit", method=RequestMethod.POST, params = "save")
	 * public ModelAndView saveEdit(HttpServletRequest request, @Valid MiscellaneousRecord miscellaneousrecord, BindingResult binding) {
	 * ModelAndView result;
	 *
	 * int curricula_id = (int) request.getSession().getAttribute("curricula_id");
	 *
	 * if (binding.hasErrors()) {
	 * result = createEditModelAndView(miscellaneousrecord, null);
	 * } else {
	 * try {
	 * miscellaneousrecordService.saveEditing(miscellaneousrecord);
	 * result = new ModelAndView("redirect:/miscellaneousrecord/candidate/list.do?q=" + curricula_id);
	 * request.getSession().removeAttribute("curricula_id");
	 * } catch (Throwable th) {
	 * result = createEditModelAndView(miscellaneousrecord, "miscellaneousrecord.commit.error");
	 * }
	 * }
	 * return result;
	 * }
	 */

	/*
	 * @RequestMapping(value = "/subject/edit", method = RequestMethod.POST, params = "save")
	 * public ModelAndView save(@Valid final Subject q, final BindingResult binding) {
	 * ModelAndView result;
	 * 
	 * if (binding.hasErrors())
	 * result = this.createEditModelAndView(q);
	 * else
	 * try {
	 * this.subjectService.save(q);
	 * result = new ModelAndView("redirect:list.do");
	 * } catch (final Throwable oops) {
	 * result = this.createEditModelAndView(q, "subject.commit.error");
	 * }
	 * 
	 * return result;
	 * }
	 */

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
