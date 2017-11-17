
package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import domain.School;
import domain.Subject;
import security.LoginService;
import services.AdministratorService;
import services.SchoolService;
import services.SubjectService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SubjectService			subjectService;

	@Autowired
	private LoginService			loginService;
	@Autowired
	private SchoolService			schoolService;

	private Administrator			toSave;


	//Edit Personal Data
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		result = this.createEditModelAndViewAdmin(this.administratorService.checkPrincipal(), null);

		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndViewAdmin(administrator, null);
		else
			try {
				final Pattern pattern = Pattern.compile("^(([+])([0-9]{2})([ ])([(][0-9]{0,3}[)])?([ ])?([0-9]{4,}))|$");
				final Matcher matcher = pattern.matcher(administrator.getPhone());
				if (!matcher.matches()) {
					result = new ModelAndView("student/confirm");
					this.toSave = administrator;
				} else {
					this.administratorService.update(administrator);
					result = new ModelAndView("redirect:/welcome/index.do");
				}
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndViewAdmin(administrator, "student.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndViewAdmin(final Administrator administrator, final String message) {
		ModelAndView result;
		result = new ModelAndView("administrator/edit");
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("administrator", administrator);
		result.addObject("message", message);
		return result;
	}

	//Mis asignaturas

	@RequestMapping(value = "/subject/list", method = RequestMethod.GET)
	public ModelAndView listAdministrator() {
		ModelAndView result;

		final Administrator a = (Administrator) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		result = new ModelAndView("subject/list");
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
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
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("subject", subjectService.findOne(q));
		return result;
	}

	//Dashboard

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("administrator/dashboard");
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
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
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("subject", subject);
		result.addObject("message", message);

		return result;
	}

}
