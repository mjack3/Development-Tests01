/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.StudentService;
import services.SubmissionService;
import controllers.AbstractController;
import forms.SubmissionForm;

@Controller
@RequestMapping("/submission/student")
public class SubmissionStudentController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public SubmissionStudentController() {
		super();
	}

	@Autowired
	private SubmissionService submissionService;
	@Autowired
	private StudentService studentService;

	// Creation --------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET, params = "assignmentId")
	public ModelAndView create(@RequestParam int assignmentId) {

		ModelAndView result;
		try {
			SubmissionForm submissionForm = submissionService
					.create(assignmentId);

			result = this.createEditModelAndView(submissionForm);
		} catch (Throwable oops) {
			result = new ModelAndView(
					"redirect:/assignment/student/list.do?studentId="
							+ studentService.checkPrincipal().getId());
		}

		return result;
	}

	// Editar

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(SubmissionForm submissionForm,
			BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			this.submissionService.reconstruct(submissionForm, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(submissionForm);
			else {
				result = new ModelAndView(
						"redirect:/assignment/student/list.do?studentId="
								+ studentService.checkPrincipal().getId());
			}
		} catch (Throwable oops) {
			if(oops.getMessage()=="error.attachment.format")result = this.createEditModelAndView(submissionForm,"submission.attachment.format.error");
			if (binding.hasErrors())
				result = this.createEditModelAndView(submissionForm);
			else
				result = this.createEditModelAndView(submissionForm,
						"submission.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(SubmissionForm submissionForm) {
		ModelAndView result;

		result = this.createEditModelAndView(submissionForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			SubmissionForm submissionForm, String message) {
		ModelAndView result;

		result = new ModelAndView("submission/student/edit");
		result.addObject("submissionForm", submissionForm);
		result.addObject("message", message);
		result.addObject("requestParam", "submission/student/edit.do");

		return result;
	}

}
