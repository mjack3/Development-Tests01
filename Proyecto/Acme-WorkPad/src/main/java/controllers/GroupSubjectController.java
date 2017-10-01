
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

import domain.GroupSubject;
import domain.Student;
import domain.Subject;
import security.LoginService;
import services.GroupSubjectService;
import services.SubjectService;

@Controller
@RequestMapping("/groupsubject")
public class GroupSubjectController {

	@Autowired
	GroupSubjectService		groupsubjectService;
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
	public ModelAndView saveCreate(@Valid GroupSubject groupsubject, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(groupsubject, null);
		} else {
			try {
				groupsubjectService.save(groupsubject, subjectId);
				result = new ModelAndView("redirect:/groupsubject/student/list.do?q=" + subjectId);
			} catch (Throwable th) {
				th.printStackTrace();
				result = createNewModelAndView(groupsubject, "groupsubject.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(GroupSubject groupsubject, String message) {
		ModelAndView result;
		result = new ModelAndView("groupsubject/create");
		result.addObject("groupsubject", groupsubject);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/student/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam Integer q) {
		ModelAndView result;

		result = new ModelAndView("groupsubject/list");
		final Student student = (Student) this.loginservice.findActorByUsername(LoginService.getPrincipal().getId());
		result.addObject("groupsubject", subjectservice.findOne(q).getGroups());
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
