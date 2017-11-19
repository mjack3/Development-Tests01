
package controllers.teacher;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Assignment;
import domain.School;
import domain.Student;
import domain.Subject;
import domain.Teacher;
import forms.AssignmentForm;
import security.LoginService;
import services.AssignmentService;
import services.SchoolService;
import services.SubjectService;

@RequestMapping("/assignment/teacher")
@Controller
public class AssigmentTeacherController extends AbstractController {

	@Autowired
	private AssignmentService	assignmentService;
	@Autowired
	private SubjectService		subjectService;
	@Autowired
	private SchoolService		schoolService;
	@Autowired
	private LoginService		loginService;


	public AssigmentTeacherController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int q) {
		ModelAndView resul;
		try {
			Teacher teacher = (Teacher) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
			Assert.isTrue(teacher.getSubjects().contains(subjectService.findOne(q)));
			final AssignmentForm assignmentForm = new AssignmentForm();
			assignmentForm.setSubjectId(q);
			resul = this.createEditModelAndView(assignmentForm);
		} catch (Throwable e) {
			resul = new ModelAndView("redirect:/welcome/index.do");
		}

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

				if (!assignmentForm.getEndDate().after(assignmentForm.getStartDate())) {
					bindingResult.rejectValue("startDate", "assignment.date.error", "error");
					bindingResult.rejectValue("endDate", "assignment.date.error", "error");

					throw new IllegalArgumentException();
				} else {
					new Date();
					if (assignmentForm.getStartDate().before(now)) {
						bindingResult.rejectValue("startDate", "assignment.date.error", "error");
						throw new IllegalArgumentException();
					}
				}

				Assignment assignment = this.assignmentService.reconstruct(assignmentForm);
				final Subject subject = this.subjectService.findOnePrincipal(assignmentForm.getSubjectId());
				//Seguridad
				if (loginService.hasRole("TEACHER")) {
					Teacher teacher = (Teacher) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
					Assert.isTrue(teacher.getSubjects().contains(subject));
				}

				if (loginService.hasRole("STUDENT")) {
					Student student = (Student) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
					Assert.isTrue(student.getSubjects().contains(subject));
				}
				assignment = this.assignmentService.save(assignment, subject);

				resul = new ModelAndView("redirect:/assignment/list.do?q=" + subject.getId());

			} catch (final Throwable oops) {
				resul = this.createEditModelAndView(assignmentForm, "assignment.commit.error");
			}

		return resul;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {

		ModelAndView resul;
		try {
			final Assignment assignment = this.assignmentService.findOnePrinicpal(q);
			final AssignmentForm assignmentForm = new AssignmentForm();

			//Seguridad
			Teacher teacher = (Teacher) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
			Boolean isAssi = false;
			for (Subject a : teacher.getSubjects()) {
				if (a.getAssigments().contains(assignment)) {
					isAssi = true;
					break;

				}
			}
			Assert.isTrue(isAssi);
			//Seguridad
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
		} catch (Throwable e) {
			resul = new ModelAndView("redirect:/welcome/index.do");
		}
		return resul;

	}

	private ModelAndView createEditModelAndView(final AssignmentForm assignmentForm) {
		// TODO Auto-generated method stub
		return this.createEditModelAndView(assignmentForm, null);
	}

	private ModelAndView createEditModelAndView(final AssignmentForm assignmentForm, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("assignment/edit");
		School school = schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());
		resul.addObject("assignmentForm", assignmentForm);
		resul.addObject("requestURI", "assignment/teacher/edit.do");

		return resul;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int subjectId) {
		ModelAndView resul;
		try {
			Assert.isTrue(subjectService.exists(subjectId));

			final Collection<Assignment> assignments = this.assignmentService.findAllPrincipalBySubjectId(subjectId);

			resul = new ModelAndView("assignment/list");
			School school = schoolService.findAll().iterator().next();
			resul.addObject("image", school.getBanner());
			resul.addObject("assigments", assignments);
			resul.addObject("subjectId", subjectId);

			resul.addObject("requestURI", "assignment/teacher/list.do");
		} catch (Throwable e) {
			resul = new ModelAndView("redirect:/welcome/index.do");
		}
		return resul;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int q) {

		ModelAndView resul;
		try {
			final Subject subject = this.subjectService.findOneByAssignment(q);
			final Assignment assignment = this.assignmentService.findOnePrinicpal(q);

			subject.getAssigments().remove(assignment);
			this.subjectService.update(subject);

			this.assignmentService.delete(assignment);
			resul = new ModelAndView("redirect:/assignment/list.do?q=" + subject.getId());

		} catch (final Throwable oops) {
			resul = new ModelAndView("redirect:/welcome/index.do");
		}

		return resul;
	}
}
