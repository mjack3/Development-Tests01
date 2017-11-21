
package controllers;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccountRepository;
import services.SchoolService;
import services.StudentService;
import domain.School;
import domain.Student;

@Controller
@RequestMapping("/student")
public class StudentController extends AbstractController {

	@Autowired
	private StudentService			studentService;
	@Autowired
	private SchoolService			schoolService;

	private Student					toSave;

	@Autowired
	private UserAccountRepository	userAccountRepository;


	public StudentController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = this.createNewModelAndView(this.studentService.create(), null);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView resul = new ModelAndView("student/list");
		final School school = this.schoolService.findAll().iterator().next();
		final Collection<Student> students = this.studentService.findAll();
		resul.addObject("students", students);
		resul.addObject("requestURI", "student/list.do");
		resul.addObject("image", school.getBanner());
		return resul;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		result = this.createEditModelAndView(this.studentService.checkPrincipal(), null);

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final Student student, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createNewModelAndView(student, null);
		else
			try {
				final Pattern pattern = Pattern.compile("^([+])([0-9]{2})([ ])([(][0-9]{0,3}[)])?([ ])?([0-9]{4,})$");
				final Matcher matcher = pattern.matcher(student.getPhone());
				if (!matcher.matches()) {
					result = new ModelAndView("student/confirm");
					final School school = this.schoolService.findAll().iterator().next();
					result.addObject("image", school.getBanner());
					this.toSave = student;
				} else if (this.userAccountRepository.findByUsername(student.getUserAccount().getUsername()) == null) {
					this.studentService.save(student);
					result = new ModelAndView("redirect:/welcome/index.do");
				} else {
					binding.rejectValue("username", "student.repeat", "un error por defecto");
					throw new IllegalArgumentException();
				}
			} catch (final Throwable th) {
				result = this.createNewModelAndView(student, "student.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Student student, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(student, null);
		else
			try {
				final Pattern pattern = Pattern.compile("^(([+])([0-9]{2})([ ])([(][0-9]{0,3}[)])?([ ])?([0-9]{4,}))|$");
				final Matcher matcher = pattern.matcher(student.getPhone());
				if (!matcher.matches()) {
					result = new ModelAndView("student/confirm");
					final School school = this.schoolService.findAll().iterator().next();
					result.addObject("image", school.getBanner());
					this.toSave = student;
				} else {
					this.studentService.update(student);
					result = new ModelAndView("redirect:/welcome/index.do");
				}
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndView(student, "student.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/saveConfirm", method = RequestMethod.POST)
	public ModelAndView saveConfirm() {
		ModelAndView result;
		try {
			this.studentService.save(this.toSave);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable th) {
			th.printStackTrace();
			result = this.createNewModelAndView(this.toSave, "student.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/saveConfirmEdit", method = RequestMethod.POST)
	public ModelAndView saveConfirmEdit() {
		ModelAndView result;
		try {
			this.studentService.update(this.toSave);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable th) {
			th.printStackTrace();
			result = this.createNewModelAndView(this.toSave, "student.commit.error");
		}

		return result;
	}

	protected ModelAndView createNewModelAndView(final Student student, final String message) {
		ModelAndView result;
		result = new ModelAndView("student/create");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("student", student);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Student student, final String message) {
		ModelAndView result;
		result = new ModelAndView("student/edit");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("student", student);
		result.addObject("message", message);
		return result;
	}

}
