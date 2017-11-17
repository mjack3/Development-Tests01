
package controllers.teacher;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.School;
import domain.Subject;
import services.SchoolService;
import services.SubjectService;
import services.TeacherService;

@Controller
@RequestMapping("/subject/teacher")
public class SubjectTeacherConrtroller extends AbstractController {

	@Autowired
	private SubjectService	subjectService;
	@Autowired
	private TeacherService	teacherService;
	@Autowired
	private SchoolService	schoolService;


	public SubjectTeacherConrtroller() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView resul = new ModelAndView("subject/list");
		School school = schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());
		final Collection<Subject> subjects = this.subjectService.findAllByPrincipal(this.teacherService.checkPrincipal());

		resul.addObject("subject", subjects);
		resul.addObject("requestURI", "subject/teacher/list.do");
		resul.addObject("requestSearch", "subject/authenticated/search.do");

		resul.addObject("principal", this.teacherService.checkPrincipal());
		return resul;
	}
}
