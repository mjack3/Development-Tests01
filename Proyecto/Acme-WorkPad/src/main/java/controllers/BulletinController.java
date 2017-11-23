
package controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import services.BulletinService;
import services.SchoolService;
import services.SubjectService;
import domain.Bulletin;
import domain.School;
import domain.Subject;
import forms.BulletinForm;

@Controller
@RequestMapping("/bulletin")
public class BulletinController extends AbstractController {

	@Autowired
	private BulletinService	bulletinService;
	@Autowired
	private SubjectService	subjectService;
	@Autowired
	private SchoolService	schoolService;
	@Autowired
	private LoginService	loginService;


	@RequestMapping(value = "/actor/create", method = RequestMethod.GET, params = "q")
	public ModelAndView create(@RequestParam final int q) {
		ModelAndView result;
		try {
			Assert.isTrue(this.subjectService.exists(q));
			BulletinForm bulletinForm;

			bulletinForm = new BulletinForm();
			bulletinForm.setSubjectId(q);
			bulletinForm.setPostedDate(new Date());
			result = this.createEditModelAndView(bulletinForm);
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	// Editar

	@RequestMapping(value = "/actor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final BulletinForm bulletinForm, final BindingResult binding, final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {

			final Bulletin bulletin = this.bulletinService.reconstruct(bulletinForm, binding);
			final Bulletin saved = this.bulletinService.save(bulletin);
			if (binding.hasErrors())
				result = this.createEditModelAndView(bulletinForm);
			else {
				final Subject subject = this.subjectService.findOne(bulletinForm.getSubjectId());
				subject.getBulletins().add(saved);
				this.subjectService.saveBulletin(subject);
				result = new ModelAndView("redirect:/bulletin/actor/list.do?q=" + subject.getId());
			}
		} catch (final Throwable oops) {
			if (binding.hasErrors())
				result = this.createEditModelAndView(bulletinForm);
			else
				result = this.createEditModelAndView(bulletinForm, "bulletin.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final BulletinForm bulletinForm) {
		ModelAndView result;

		result = this.createEditModelAndView(bulletinForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final BulletinForm bulletinForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("bulletin/actor/edit");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("bulletinForm", bulletinForm);
		result.addObject("message", message);
		result.addObject("requestParam", "bulletin/actor/edit.do");

		return result;
	}

	protected ModelAndView createNewModelAndView(final Bulletin bulletin, final String message) {
		ModelAndView result;
		result = new ModelAndView("bulletin/create");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("bulletin", bulletin);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/actor/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer q) {
		ModelAndView result;
		try {
			Assert.isTrue(this.subjectService.exists(q));
			result = new ModelAndView("bulletin/list");
			final School school = this.schoolService.findAll().iterator().next();
			result.addObject("image", school.getBanner());
			result.addObject("id", q);
			result.addObject("bulletin", this.bulletinService.findBySubject(q));
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Bulletin bulletin, final String message) {
		final ModelAndView result = new ModelAndView("bulletin/edit");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("bulletin", bulletin);
		result.addObject("message", message);

		return result;
	}

}
