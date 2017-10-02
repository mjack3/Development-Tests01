
package controllers.teacher;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SubjectService;
import services.TeacherService;
import controllers.AbstractController;
import domain.Subject;

@Controller
@RequestMapping("/subject/teacher")
public class SubjectTeacherConrtroller extends AbstractController {

	@Autowired
	private SubjectService	subjectService;
	@Autowired
	private TeacherService	teacherService;


	public SubjectTeacherConrtroller() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView resul = new ModelAndView("subject/list");
		final Collection<Subject> subjects = this.subjectService.findAllByPrincipal(this.teacherService.checkPrincipal());

		resul.addObject("subject", subjects);
		resul.addObject("requestURI", "subject/teacher/list.do");
		resul.addObject("requestSearch", "subject/authenticated/search.do");

		resul.addObject("principal", this.teacherService.checkPrincipal());
		return resul;
	}
}
