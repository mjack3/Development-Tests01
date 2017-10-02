/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.actor;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.BulletinService;
import services.SubjectService;
import controllers.AbstractController;
import domain.Bulletin;
import domain.Subject;
import forms.BulletinForm;

@Controller
@RequestMapping("/actor/bulletin")
public class ActorBulletinController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ActorBulletinController() {
		super();
	}

	@Autowired
	private BulletinService bulletinService;
	@Autowired
	private SubjectService subjectService;

	// Creation --------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET, params = "subjectId")
	public ModelAndView create(@RequestParam int subjectId) {
		ModelAndView result;
		BulletinForm bulletinForm;
		
		bulletinForm = new BulletinForm();
		bulletinForm.setSubjectId(subjectId);
		bulletinForm.setPostedDate(new Date());
		result = this.createEditModelAndView(bulletinForm);
		return result;
	}

	// Editar

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(BulletinForm bulletinForm, BindingResult binding,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;
		

		try {
			Bulletin bulletin = this.bulletinService.reconstruct(bulletinForm, binding);
			Bulletin saved = bulletinService.save(bulletin);
			if (binding.hasErrors())
				result = this.createEditModelAndView(bulletinForm);
			else {
				Subject subject = subjectService.findOne(bulletinForm.getSubjectId());
				subject.getBulletins().add(saved);
				subjectService.save(subject);
				result = new ModelAndView(
						"redirect:/subject/display.do?subjectId=" + subject.getId());
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
			}
		} catch (Throwable oops) {
			if (binding.hasErrors())
				result = this.createEditModelAndView(bulletinForm);
			else
				result = this.createEditModelAndView(bulletinForm,
						"actor.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(BulletinForm bulletinForm) {
		ModelAndView result;

		result = this.createEditModelAndView(bulletinForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(BulletinForm bulletinForm,
			String message) {
		ModelAndView result;

		result = new ModelAndView("actor/bulletin/edit");
		result.addObject("bulletinForm", bulletinForm);
		result.addObject("message", message);
		result.addObject("requestParam", "actor/bulletin/edit.do");

		return result;
	}

}
