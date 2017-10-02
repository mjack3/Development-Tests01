
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.GroupService;
import services.SubjectService;
import domain.Group;
import domain.Student;
import domain.Subject;

@Controller
@RequestMapping("/group")
public class GroupController {

	@Autowired
	GroupService		groupsubjectService;
	@Autowired
	LoginService			loginservice;
	@Autowired
	private SubjectService	subjectservice;

	private Integer			subjectId		= null;
	private Boolean			subjectscond	= false;


	@RequestMapping(value = "/student/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		if (subjectscond) {
			result = createNewModelAndView(groupsubjectService.create(), null);
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/student/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Group groupsubject, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(groupsubject, null);
		} else {
			try {
				groupsubjectService.save(groupsubject, subjectId);
				result = new ModelAndView("redirect:/group/student/list.do?q=" + subjectId);
			} catch (Throwable th) {
				th.printStackTrace();
				result = createNewModelAndView(groupsubject, "groupsubject.commit.error");
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

		result = new ModelAndView("group/list");
		final Student student = (Student) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());
		result.addObject("group", subjectservice.findOne(q).getGroups());
		List<Subject> subjects = new ArrayList<Subject>();
		subjectscond = false;

		for (Subject e : student.getSubjects()) {
			subjects.add(e);
		}
		if (subjects.contains(subjectservice.findOne(q))) {
			subjectscond = true;
		}

		result.addObject("subjectscond", subjectscond);

		subjectId = q;

		return result;
	}

}