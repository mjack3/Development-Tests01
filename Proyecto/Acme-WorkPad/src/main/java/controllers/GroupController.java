
package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Group;
import domain.Student;
import domain.Subject;
import domain.Teacher;
import security.LoginService;
import services.GroupService;
import services.StudentService;
import services.SubjectService;

@Controller
@RequestMapping("/group")
public class GroupController {

	@Autowired
	GroupService			grouptService;
	@Autowired
	LoginService			loginservice;
	@Autowired
	private SubjectService	subjectservice;
	@Autowired
	private StudentService	studentService;

	private Integer			subjectId		= null;
	private Boolean			subjectscond	= false;


	@RequestMapping(value = "/student/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		if (subjectscond) {
			result = createNewModelAndView(grouptService.create(), null);
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/student/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Group groupsubject, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			for (ObjectError e : binding.getAllErrors()) {
				System.out.println(e.toString());
			}
			result = createNewModelAndView(groupsubject, null);
		} else {
			try {

				grouptService.save(groupsubject, subjectId);
				result = new ModelAndView("redirect:/group/student/list.do?q=" + subjectId);
			} catch (Throwable th) {
				th.printStackTrace();
				result = createNewModelAndView(groupsubject, "acme.error.message");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(Group groupsubject, String message) {
		ModelAndView result;
		result = new ModelAndView("group/create");
		result.addObject("group", groupsubject);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/student/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam Integer q) {
		ModelAndView result;
		Date today = new Date();

		result = new ModelAndView("group/list");
		result.addObject("requestURL", "/group/student/list");
		Student student = null;
		Teacher teacher = null;
		if (LoginService.hasRole("STUDENT")) {
			student = (Student) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());

			System.out.println("-------------------- lista general-----------------");
			boolean isgroup = false;
			for (Group g : grouptService.findAll()) {

				System.out.println(!student.getGroups().contains(g));
				isgroup = false;
				if (!grouptService.studentByGroups(student.getId()).contains(g) && student.getSubjects().contains((subjectservice.findOne(q)))) {

					System.out.println(subjectservice.subjectsByStudents(student.getId()).contains(subjectservice.findOne(q)));
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

		{
			teacher = (Teacher) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());

		}

		result.addObject("group", subjectservice.findOne(q).getGroups());
		List<Subject> subjects = new ArrayList<Subject>();
		subjectscond = false;

		if (student != null) {

			for (Subject e : student.getSubjects()) {
				subjects.add(e);
			}
			boolean isgroup = false;

			System.out.println("--------------------mi lista -----------------");
			for (Group g : grouptService.findAll()) {
				isgroup = false;
				System.out.println(subjects.contains(subjectservice.findOne(q)));
				for (Student t : subjectservice.findOne(q).getStudents()) {
					System.out.println(t.getName());
				}

				if (student.getSubjects().contains((subjectservice.findOne(q)))) {
					System.out.println(subjects.contains(subjectservice.findOne(q)));
					System.out.println(!student.getGroups().contains(g));
					isgroup = true;
				}
				if (grouptService.studentByGroups(student.getId()).contains(g)) {
					isgroup = false;
					break;
				}

			}
			result.addObject("isgroup", isgroup);
			System.out.println("--------------------/mi lista -----------------");

			result.addObject("today", today);
		}
		if (teacher != null) {
			for (Subject e : teacher.getSubjects()) {
				subjects.add(e);
			}
		}

		if (subjects.contains(subjectservice.findOne(q))) {
			subjectscond = true;
		}

		result.addObject("subjectscond", subjectscond);

		subjectId = q;

		return result;
	}

	//Para Suscribirse
	@RequestMapping(value = "/student/subscribe", method = RequestMethod.GET)
	public ModelAndView subscribe(@RequestParam final Group q) {
		ModelAndView result;
		Date today = new Date();

		final Student student = (Student) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());

		q.getStudents().add(student);
		this.grouptService.save(q);
		final List<Group> groups = student.getGroups();
		groups.add(q);
		student.setGroups(groups);

		this.studentService.update(student);

		result = new ModelAndView("redirect:/group/student/mylist.do");
		return result;
	}

	@RequestMapping(value = "/student/mylist", method = RequestMethod.GET)
	public ModelAndView myList() {
		ModelAndView result;

		result = new ModelAndView("group/list");
		result.addObject("requestURL", "/group/student/mylist");
		Student student = null;

		student = (Student) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());

		result.addObject("group", student.getGroups());

		return result;
	}

}
