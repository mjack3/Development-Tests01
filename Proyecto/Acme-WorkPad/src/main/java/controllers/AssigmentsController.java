
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Assignment;
import domain.School;
import domain.Subject;
import domain.Teacher;
import security.LoginService;
import services.SchoolService;
import services.SubjectService;

@Controller
@RequestMapping("/assignment")
public class AssigmentsController extends AbstractController {

	@Autowired
	private SubjectService	subjectService;

	@Autowired
	private LoginService	loginService;

	@Autowired
	private SchoolService	schoolService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int q) {
		ModelAndView resul;
		try {
			resul = new ModelAndView("assignment/list");
			resul.addObject("requestURI", "assignment/list.do");
			Subject subject = this.subjectService.findOnePrincipal(q);
			resul.addObject("assigments", subject.getAssigments());
			School school = schoolService.findAll().iterator().next();
			resul.addObject("image", school.getBanner());

			if (loginService.hasRole("TEACHER")) {
				final Teacher teacher = (Teacher) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

				List<Assignment> activities = new ArrayList<Assignment>();
				for (Subject a : teacher.getSubjects()) {
					activities.addAll(a.getAssigments());

				}

				resul.addObject("myactivities", activities);
			}
		} catch (Throwable e) {
			resul = new ModelAndView("redirect:/welcome/index.do");
		}

		return resul;
	}

}
