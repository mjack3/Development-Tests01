
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Activity;
import domain.School;
import domain.Subject;
import domain.Teacher;
import security.LoginService;
import services.ActivityService;
import services.SchoolService;
import services.SubjectService;

@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController {

	@Autowired
	private ActivityService	activity;
	@Autowired
	private LoginService	loginservice;
	@Autowired
	private SubjectService	subjectService;
	@Autowired
	private SchoolService	schoolService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int q) {
		ModelAndView resul = new ModelAndView("activity/list");

		Subject subject = this.subjectService.findOnePrincipal(q);
		resul.addObject("activities", subject.getActivities());
		School school = schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());

		if (loginservice.hasRole("TEACHER")) {
			final Teacher teacher = (Teacher) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());
			if (teacher.getSubjects().contains(subject)) {
				resul.addObject("ismySubject", true);
			} else {
				resul.addObject("ismySubject", false);
			}

			List<Activity> activities = new ArrayList<Activity>();
			for (Subject a : teacher.getSubjects()) {
				activities.addAll(a.getActivities());

			}

			resul.addObject("myactivities", activities);
		}

		return resul;
	}
}
