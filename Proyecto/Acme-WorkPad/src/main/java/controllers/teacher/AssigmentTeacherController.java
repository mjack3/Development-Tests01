
package controllers.teacher;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AssignmentService;
import services.SubjectService;
import controllers.AbstractController;
import domain.Assignment;
import domain.Subject;
import forms.AssignmentForm;

@RequestMapping("/assignment/teacher")
@Controller
public class AssigmentTeacherController extends AbstractController {

	@Autowired
	private AssignmentService	assignmentService;
	@Autowired
	private SubjectService		subjectService;


	public AssigmentTeacherController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int subjectId) {
		ModelAndView resul;

		final AssignmentForm assignmentForm = new AssignmentForm();
		assignmentForm.setSubjectId(subjectId);
		resul = this.createEditModelAndView(assignmentForm);

		return resul;
	}

	/*
	 * EDIT
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AssignmentForm assignmentForm, final BindingResult bindingResult) {
		ModelAndView resul;

		if (bindingResult.hasErrors())
			resul = this.createEditModelAndView(assignmentForm);
		else
			try {

				/*
				 * más comprobaciones
				 */
				final Date now = new Date();
				if (!assignmentForm.getEndDate().after(assignmentForm.getStartDate()) || assignmentForm.getStartDate().before(now)) {
					bindingResult.rejectValue("startDate", "assignment.date.error", "error");
					bindingResult.rejectValue("endDate", "assignment.date.error", "error");

					throw new IllegalArgumentException();
				}

				Assignment assignment = this.assignmentService.reconstruct(assignmentForm);
				final Subject subject = this.subjectService.findOnePrincipal(assignmentForm.getSubjectId());
				assignment = this.assignmentService.save(assignment, subject);

				resul = new ModelAndView("redirect:/assignment/teacher/list.do?subjectId=" + subject.getId());

			} catch (final Throwable oops) {
				resul = this.createEditModelAndView(assignmentForm, "assignment.commit.error");
			}

		return resul;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {

		ModelAndView resul;

		final Assignment assignment = this.assignmentService.findOnePrinicpal(q);
		final AssignmentForm assignmentForm = new AssignmentForm();

		assignmentForm.setDescription(assignment.getDescription());
		assignmentForm.setTitle(assignment.getTitle());
		assignmentForm.setEndDate(assignment.getEndDate());
		assignmentForm.setLink(assignment.getLink());
		assignmentForm.setStartDate(assignment.getStartDate());
		assignmentForm.setId(assignment.getId());
		assignmentForm.setVersion(assignment.getVersion());

		final Subject subject = this.subjectService.findOneByAssignment(q);
		assignmentForm.setSubjectId(subject.getId());

		resul = this.createEditModelAndView(assignmentForm);
		return resul;

	}

	private ModelAndView createEditModelAndView(final AssignmentForm assignmentForm) {
		// TODO Auto-generated method stub
		return this.createEditModelAndView(assignmentForm, null);
	}

	private ModelAndView createEditModelAndView(final AssignmentForm assignmentForm, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("assignment/edit");
		resul.addObject("assignmentForm", assignmentForm);
		resul.addObject("requestURI", "assignment/teacher/edit.do");

		return resul;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int subjectId) {
		ModelAndView resul;

		final Collection<Assignment> assignments = this.assignmentService.findAllPrincipalBySubjectId(subjectId);

		resul = new ModelAndView("assignment/list");
		resul.addObject("assigments", assignments);
		resul.addObject("subjectId", subjectId);

		resul.addObject("requestURI", "assignment/teacher/list.do");
		return resul;
	}
}
