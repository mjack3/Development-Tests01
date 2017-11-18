
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

import controllers.AbstractController;
import domain.Activity;
import domain.School;
import domain.Subject;
import forms.ActivityForm;
import services.ActivityService;
import services.SchoolService;
import services.SubjectService;
import services.TeacherService;

@Controller
@RequestMapping("/activity/teacher")
public class ActivityTeacherController extends AbstractController {

	@Autowired
	private TeacherService	teacherService;

	@Autowired
	private ActivityService	activityService;
	@Autowired
	private SubjectService	subjectService;
	@Autowired
	private SchoolService	schoolService;


	public ActivityTeacherController() {
		super();
	}

	/*
	 * CREATE ========================
	 */

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int q) {
		final ModelAndView resul;

		final ActivityForm activityForm = new ActivityForm();
		activityForm.setSubjectId(q);
		resul = this.createCreateModelAndView(activityForm);

		return resul;
	}

	@RequestMapping(value = "create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final ActivityForm activityForm, final BindingResult bindingResult) {
		ModelAndView resul;

		if (bindingResult.hasErrors())
			resul = this.createCreateModelAndView(activityForm);
		else
			try {

				/*
				 * otras comprobaciones
				 */

				if (!activityForm.getEndDate().after(activityForm.getStartDate())) {
					bindingResult.rejectValue("startDate", "activity.date.error", "error");
					bindingResult.rejectValue("endDate", "activity.date.error", "error");
					throw new IllegalArgumentException();
				} else if (!activityForm.getStartDate().after(new Date(System.currentTimeMillis() - 1))) {
					bindingResult.rejectValue("startDate", "activity.date.error", "error");
					throw new IllegalArgumentException();
				}

				final Activity activity = this.activityService.reconstruct(activityForm);

				final Subject subject = this.subjectService.findOnePrincipal(activityForm.getSubjectId());
				this.activityService.save(activity, subject);
				resul = new ModelAndView("redirect:/activity/list.do?q=" + subject.getId());

			} catch (final Throwable oops) {
				resul = this.createCreateModelAndView(activityForm, "activity.commit.error");
			}

		return resul;
	}
	private ModelAndView createCreateModelAndView(final ActivityForm activityForm) {
		// TODO Auto-generated method stub
		return this.createCreateModelAndView(activityForm, null);
	}

	private ModelAndView createCreateModelAndView(final ActivityForm activityForm, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("activity/create");
		School school = schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());
		resul.addObject("activityForm", activityForm);

		resul.addObject("message", message);
		resul.addObject("requestURI", "teacher/activity/create.do");

		return resul;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int q) {
		final ModelAndView resul = new ModelAndView("activity/list");
		School school = schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());

		final Subject subject = this.subjectService.findOnePrincipal(q);
		final Collection<Activity> activities = this.teacherService.findAllActivitiesBySubject(subject);

		resul.addObject("activities", activities);
		resul.addObject("requestURI", "activity/teacher/list.do");

		return resul;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {
		final Activity activity = this.activityService.findOnePrincipal(q);
		final ModelAndView resul = this.createEditModelAndView(activity);

		return resul;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Activity activity, final BindingResult bindingResult) {
		ModelAndView resul;

		if (bindingResult.hasErrors())
			resul = this.createEditModelAndView(activity);
		else
			try {

				/*
				 * otras comprobaciones
				 */

				if ((!activity.getEndDate().after(activity.getStartDate())) || (activity.getStartDate().before(new Date()))) {
					bindingResult.rejectValue("startDate", "activity.date.error", "error");
					bindingResult.rejectValue("endDate", "activity.date.error", "error");
					throw new IllegalArgumentException();
				}

				this.activityService.save(activity);
				final Subject subject = this.subjectService.findSubjectByTeacherIdActivityId(this.teacherService.checkPrincipal().getId(), activity.getId());
				resul = new ModelAndView("redirect:/activity/list.do?q=" + subject.getId());

			} catch (final Throwable oops) {
				resul = this.createEditModelAndView(activity, "activity.commit.error");
			}

		return resul;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int q) {

		ModelAndView resul;
		try {

			final Activity activity = this.activityService.findOnePrincipal(q);
			final Subject subject = this.subjectService.findSubjectByTeacherIdActivityId(this.teacherService.checkPrincipal().getId(), activity.getId());

			this.activityService.delete(activity);
			resul = new ModelAndView("redirect:/activity/list.do?q=" + subject.getId());

		} catch (final Throwable oops) {
			resul = new ModelAndView("redirect:/welcome/index.do");
			resul.addObject("message", "activity.commit.error");
		}

		return resul;
	}

	private ModelAndView createEditModelAndView(final Activity activity) {
		// TODO Auto-generated method stub
		return this.createEditModelAndView(activity, null);
	}

	private ModelAndView createEditModelAndView(final Activity activity, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("activity/edit");
		School school = schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());

		resul.addObject("activity", activity);

		resul.addObject("message", message);
		resul.addObject("requestURI", "activity/teacher/edit.do");
		resul.addObject("type", "edit");
		return resul;
	}
}
