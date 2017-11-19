
package controllers.student;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.School;
import services.AssignmentService;
import services.SchoolService;

@RequestMapping("/assignment/student")
@Controller
public class AssigmentStudentController extends AbstractController {

	@Autowired
	private AssignmentService	assignmentService;
	@Autowired
	private SchoolService		schoolService;


	public AssigmentStudentController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Date today = new Date();
		try {
			result = new ModelAndView("assignment/student/list");
			School school = schoolService.findAll().iterator().next();
			result.addObject("image", school.getBanner());
			result.addObject("assignments", assignmentService.findAllByPrincipalStudent());
			result.addObject("today", today);
		} catch (Throwable oops) {
			result = new ModelAndView("index.do");
		}

		return result;
	}
}
