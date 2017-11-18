
package controllers.teacher;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AssignmentService;
import services.GroupService;
import services.SchoolService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.School;
import domain.Submission;

@RequestMapping("/submission/teacher")
@Controller
public class SubmissionTeacherController extends AbstractController {

	@Autowired
	private SubmissionService	submissionService;
	@Autowired
	private SchoolService		schoolService;
	@Autowired
	private GroupService		groupService;

	@Autowired
	private AssignmentService	assignmentService;

	private static String		imageSchool;


	public SubmissionTeacherController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int q) {
		final ModelAndView resul = new ModelAndView("submission/list");
		final School school = this.schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());

		final Collection<Submission> submissions = this.groupService.findOne(q).getSubmission();
		resul.addObject("submissions", submissions);
		resul.addObject("requestURI", "submission/teacher/list.do");

		return resul;
	}

	@RequestMapping(value = "/listTask", method = RequestMethod.GET)
	public ModelAndView listTask(@RequestParam final int q) {
		final ModelAndView resul = new ModelAndView("submission/list");
		final School school = this.schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());

		final Collection<Submission> submissions = this.assignmentService.findOne(q).getSubmission();
		resul.addObject("submissions", submissions);
		resul.addObject("requestURI", "submission/teacher/list.do");

		return resul;
	}

	@RequestMapping(value = "/grade", method = RequestMethod.GET)
	public ModelAndView grade(@RequestParam final int submissionId) {
		final ModelAndView resul;

		final Submission submission = this.submissionService.findOnePrincipal(submissionId);

		resul = this.createGradeModelAndView(submission);
		return resul;
	}

	@RequestMapping(value = "/grade", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Submission submission, final BindingResult bindingResult) {

		ModelAndView resul;
		try {

			submission = this.submissionService.reconstruct(submission, bindingResult);

			if (bindingResult.hasErrors())
				resul = this.createGradeModelAndView(submission);
			else {

				this.submissionService.save(submission);
				resul = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Exception e) {
			resul = this.createGradeModelAndView(submission, "submission.commit.error");
		}

		return resul;
	}

	private ModelAndView createGradeModelAndView(final Submission submission) {
		// TODO Auto-generated method stub
		return this.createGradeModelAndView(submission, null);
	}

	private ModelAndView createGradeModelAndView(final Submission submission, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("submission/grade");

		if (null == SubmissionTeacherController.imageSchool)
			SubmissionTeacherController.imageSchool = this.schoolService.findBannerSchool();

		resul.addObject("image", SubmissionTeacherController.imageSchool);
		resul.addObject("submission", submission);
		resul.addObject("requestParam", "submission/teacher/grade.do");
		resul.addObject("message", message);
		return resul;
	}
}
