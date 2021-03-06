
package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SchoolService;
import services.TeacherService;
import domain.School;
import domain.Teacher;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends AbstractController {

	@Autowired
	private TeacherService	teacherService;
	@Autowired
	private SchoolService	schoolService;

	private Teacher			toSave;


	public TeacherController() {
		super();
	}

	@RequestMapping("/view")
	public ModelAndView view(@RequestParam final Teacher q) {

		try {

			Assert.isTrue(this.teacherService.exists(q.getId()));
			ModelAndView res;
			res = new ModelAndView("teacher/view");
			final School school = this.schoolService.findAll().iterator().next();
			res.addObject("image", school.getBanner());
			res.addObject("teacher", q);
			return res;
		} catch (final Throwable e) {
			final ModelAndView res = new ModelAndView("redirect:/welcome/index.do");
			return res;

		}

	}

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = this.createNewModelAndView(this.teacherService.create(), null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		result = this.createEditModelAndView(this.teacherService.checkPrincipal(), null);

		return result;
	}

	@RequestMapping(value = "/administrator/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final Teacher teacher, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createNewModelAndView(teacher, null);
		else
			try {
				final Pattern pattern = Pattern.compile("^((\\+\\d{2}(\\(\\d{1,3}\\))?)?\\d{4,40})?$");
				final Matcher matcher = pattern.matcher(teacher.getPhone());
				if (!matcher.matches()) {
					result = new ModelAndView("teacher/confirm");
					final School school = this.schoolService.findAll().iterator().next();
					result.addObject("image", school.getBanner());
					this.toSave = teacher;
				} else {
					this.teacherService.save(teacher);
					result = new ModelAndView("redirect:/welcome/index.do");
				}
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createNewModelAndView(teacher, "student.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Teacher teacher, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(teacher, null);
		else
			try {
				final Pattern pattern = Pattern.compile("^([+])([0-9]{2})([ ])([(][0-9]{0,3}[)])?([ ])?([0-9]{4,})$");
				final Matcher matcher = pattern.matcher(teacher.getPhone());
				if (!matcher.matches()) {
					result = new ModelAndView("teacher/confirm");
					final School school = this.schoolService.findAll().iterator().next();
					result.addObject("image", school.getBanner());
					this.toSave = teacher;
				} else {
					this.teacherService.save(teacher);
					result = new ModelAndView("redirect:/welcome/index.do");
				}
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndView(teacher, "student.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("teacher/list");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("teachers", this.teacherService.findAll());

		return result;
	}

	@RequestMapping(value = "/saveConfirm", method = RequestMethod.POST)
	public ModelAndView saveConfirm() {
		ModelAndView result;
		try {
			this.teacherService.save(this.toSave);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable th) {
			th.printStackTrace();
			result = this.createNewModelAndView(this.toSave, "teacher.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/saveConfirmEdit", method = RequestMethod.POST)
	public ModelAndView saveConfirmEdit() {
		ModelAndView result;
		try {
			this.teacherService.save(this.toSave);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable th) {
			th.printStackTrace();
			result = this.createNewModelAndView(this.toSave, "teacher.commit.error");
		}

		return result;
	}

	protected ModelAndView createNewModelAndView(final Teacher teacher, final String message) {
		ModelAndView result;
		result = new ModelAndView("teacher/create");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("teacher", teacher);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Teacher teacher, final String message) {
		ModelAndView result;
		result = new ModelAndView("teacher/edit");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("teacher", teacher);
		result.addObject("message", message);
		return result;
	}

}
