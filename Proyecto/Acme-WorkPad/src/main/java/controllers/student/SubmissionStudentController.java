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

import security.LoginService;
import services.SchoolService;
import services.StudentService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Group;
import domain.School;
import domain.Submission;
import forms.SubmissionForm;

@Controller
@RequestMapping("/submission/student")
public class SubmissionStudentController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public SubmissionStudentController() {
		super();
	}


	@Autowired
	private SubmissionService	submissionService;
	@Autowired
	private StudentService		studentService;
	@Autowired
	private SchoolService		schoolService;
	@Autowired
	private LoginService		loginservice;


	// Creation --------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int assignmentId) {

		ModelAndView result;
		try {
			final SubmissionForm submissionForm = this.submissionService.create(assignmentId);

			result = this.createEditModelAndView(submissionForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/assignment/student/list.do?studentId=" + this.studentService.checkPrincipal().getId());
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Group q) {

		ModelAndView result;

		result = new ModelAndView("submission/list");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("submissions", q.getSubmission());

		return result;
	}

	// Editar

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final SubmissionForm submissionForm, final BindingResult binding, final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {

			final Submission submission = this.submissionService.reconstruct(submissionForm, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(submissionForm);
			else
				//this.submissionService.save(submission);
				result = new ModelAndView("redirect:/assignment/student/list.do?studentId=" + this.studentService.checkPrincipal().getId());

		} catch (final Throwable oop) {
			result = this.createEditModelAndView(submissionForm, "submission.attachment.format.error");
		}

		/*
		 * try {
		 * 
		 * this.submissionService.reconstruct(submissionForm, binding);
		 * 
		 * if (binding.hasErrors())
		 * result = this.createEditModelAndView(submissionForm);
		 * else
		 * result = new ModelAndView("redirect:/assignment/student/list.do?studentId=" + this.studentService.checkPrincipal().getId());
		 * } catch (final Throwable oops) {
		 * if (oops.getMessage() == "error.attachment.format")
		 * result = this.createEditModelAndView(submissionForm, "submission.attachment.format.error");
		 * if (binding.hasErrors())
		 * result = this.createEditModelAndView(submissionForm);
		 * else
		 * result = this.createEditModelAndView(submissionForm, "submission.commit.error");
		 * }
		 */
		return result;
	}

	protected ModelAndView createEditModelAndView(final SubmissionForm submissionForm) {
		ModelAndView result;

		result = this.createEditModelAndView(submissionForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SubmissionForm submissionForm, final String message) {

		ModelAndView result;

		result = new ModelAndView("submission/student/edit");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("submissionForm", submissionForm);
		result.addObject("message", message);
		result.addObject("requestParam", "submission/student/edit.do");

		return result;
	}

}
