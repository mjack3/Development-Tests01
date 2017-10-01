
package controllers.teacher;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.SubjectService;
import services.TeacherService;
import controllers.AbstractController;
import domain.Activity;
import domain.Subject;

@Controller
@RequestMapping("/activity/teacher")
public class ActivityTeacherController extends AbstractController {

	@Autowired
	private TeacherService	teacherService;

	@Autowired
	private ActivityService	activityService;
	@Autowired
	private SubjectService	subjectService;


	public ActivityTeacherController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int subjectId) {
		final ModelAndView resul = new ModelAndView("activity/list");

		final Subject subject = this.subjectService.findOnePrincipal(subjectId);
		final Collection<Activity> activities = this.teacherService.findAllActivitiesBySubject(subject);

		resul.addObject("activities", activities);
		resul.addObject("requestURI", "activity/teacher/list.do");

		return resul;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {
		final Activity activity = this.activityService.findOnePrincipal(q);
		final ModelAndView resul = this.createEditModelAndView(activity);

		resul.addObject("requestURI", "activity/teacher/edit.do");
		return resul;
	}

	private ModelAndView createEditModelAndView(final Activity activity) {
		// TODO Auto-generated method stub
		return this.createEditModelAndView(activity, null);
	}

	private ModelAndView createEditModelAndView(final Activity activity, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("activity/edit");

		resul.addObject("activity", activity);
		resul.addObject("message", message);
		return resul;
	}
}
