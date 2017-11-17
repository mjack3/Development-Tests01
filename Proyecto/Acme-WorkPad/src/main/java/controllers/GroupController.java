
package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Group;
import domain.School;
import domain.Student;
import domain.Subject;
import domain.Teacher;
import security.LoginService;
import services.GroupService;
import services.SchoolService;
import services.StudentService;
import services.SubjectService;

@Controller
@RequestMapping("/group")
public class GroupController extends AbstractController {

	@Autowired
	GroupService			grouptService;
	@Autowired
	LoginService			loginservice;
	@Autowired
	private SubjectService	subjectservice;
	@Autowired
	private StudentService	studentService;
	@Autowired
	private SchoolService	schoolService;

	private Integer			subjectId		= null;
	private Boolean			subjectscond	= false;


	@RequestMapping(value = "/student/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int subjectId) {
		ModelAndView resul;

		final Student principal = this.studentService.checkPrincipal();
		final boolean sw;
		final Subject subject = this.subjectservice.findOne(subjectId);

		sw = CollectionUtils.containsAny(principal.getGroups(), subject.getGroups());

		if (!sw)
			resul = this.createNewModelAndView(this.grouptService.create(), subjectId, null);
		else {
			resul = new ModelAndView("master.page");
			School school = schoolService.findAll().iterator().next();
			resul.addObject("image", school.getBanner());
			resul.addObject("message", "groupAlreadyExist");
		}

		return resul;
	}

	@RequestMapping(value = "/student/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final Group groupsubject, @RequestParam final int subjectId, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.toString());
			result = this.createNewModelAndView(groupsubject, subjectId, null);
		} else
			try {

				final Subject subject = this.subjectservice.findOne(subjectId);
				Assert.isTrue(this.studentService.checkPrincipal().getSubjects().contains(subject));

				if (groupsubject.getStartDate().after(groupsubject.getEndDate())) {
					binding.rejectValue("endDate", "dateError12", "error");
					throw new IllegalArgumentException();
				} else if (groupsubject.getStartDate().before(new Date())) {
					binding.rejectValue("endDate", "dateError11.dateError11", "people can join with 24h in advance");

					throw new IllegalArgumentException();
				}

				this.grouptService.save(groupsubject, subjectId);
				result = new ModelAndView("redirect:/group/student/list.do?q=" + subjectId);
			} catch (final Throwable th) {
				th.printStackTrace();
				result = this.createNewModelAndView(groupsubject, subjectId, "group.commit.error");
			}
		return result;
	}

	protected ModelAndView createNewModelAndView(final Group groupsubject, final int subjectId, final String message) {
		ModelAndView result;
		result = new ModelAndView("group/create");
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("group", groupsubject);
		result.addObject("message", message);
		result.addObject("subjectId", subjectId);
		return result;
	}

	@RequestMapping(value = "/student/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer q) {
		ModelAndView result;
		final Date today = new Date();

		result = new ModelAndView("group/list");
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("requestURL", "/group/student/list");
		Student student = null;
		Teacher teacher = null;
		if (LoginService.hasRole("STUDENT")) {
			student = (Student) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());

			System.out.println("-------------------- lista general-----------------");
			boolean isgroup = false;
			for (final Group g : this.grouptService.findAll()) {

				System.out.println(!student.getGroups().contains(g));
				isgroup = false;
				if (!this.grouptService.studentByGroups(student.getId()).contains(g) && student.getSubjects().contains((this.subjectservice.findOne(q)))) {

					System.out.println(this.subjectservice.subjectsByStudents(student.getId()).contains(this.subjectservice.findOne(q)));
					System.out.println(!student.getGroups().contains(g));

					isgroup = true;

				} else {
					isgroup = false;
					break;
				}
			}
			result.addObject("isgroup", isgroup);
			System.out.println("-------------------- /lista general-----------------");

		}

		if (LoginService.hasRole("TEACHER"))
			teacher = (Teacher) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());

		result.addObject("group", this.subjectservice.findOne(q).getGroups());
		final List<Subject> subjects = new ArrayList<Subject>();
		this.subjectscond = false;

		if (student != null) {

			for (final Subject e : student.getSubjects())
				subjects.add(e);
			boolean isgroup = false;

			System.out.println("--------------------mi lista -----------------");
			for (final Group g : this.grouptService.findAll()) {
				isgroup = false;
				System.out.println(subjects.contains(this.subjectservice.findOne(q)));
				for (final Student t : this.subjectservice.findOne(q).getStudents())
					System.out.println(t.getName());

				if (student.getSubjects().contains((this.subjectservice.findOne(q)))) {
					System.out.println(subjects.contains(this.subjectservice.findOne(q)));
					System.out.println(!student.getGroups().contains(g));
					isgroup = true;
				}
				if (this.grouptService.studentByGroups(student.getId()).contains(g)) {
					isgroup = false;
					break;
				}

			}
			result.addObject("isgroup", isgroup);
			System.out.println("--------------------/mi lista -----------------");

			result.addObject("today", today);
		}
		if (teacher != null)
			for (final Subject e : teacher.getSubjects())
				subjects.add(e);

		if (subjects.contains(this.subjectservice.findOne(q)))
			this.subjectscond = true;

		result.addObject("subjectscond", this.subjectscond);

		this.subjectId = q;

		return result;
	}

	//Para Suscribirse
	@RequestMapping(value = "/student/subscribe", method = RequestMethod.GET)
	public ModelAndView subscribe(@RequestParam final Group q) {
		ModelAndView result;

		try {

			final Student student = (Student) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());

			final Subject s = this.subjectservice.findOneByGroupId(q.getId());

			for (final Group group : s.getGroups())
				if (group.getStudents().contains(student))
					throw new IllegalArgumentException();

			Assert.isTrue(!q.getStudents().contains(student));

			q.getStudents().add(student);
			this.grouptService.save(q);
			final List<Group> groups = student.getGroups();
			groups.add(q);
			student.setGroups(groups);

			this.studentService.update(student);

			result = new ModelAndView("redirect:/group/student/mylist.do");

		} catch (final Throwable oops) {

			result = this.myList();
			result.addObject("message", "groupAlreadyExist");
		}

		return result;
	}

	@RequestMapping(value = "/student/mylist", method = RequestMethod.GET)
	public ModelAndView myList() {
		ModelAndView result;

		result = new ModelAndView("group/list");
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("requestURL", "/group/student/mylist");
		Student student = null;

		student = (Student) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());

		result.addObject("group", student.getGroups());

		return result;
	}

}
