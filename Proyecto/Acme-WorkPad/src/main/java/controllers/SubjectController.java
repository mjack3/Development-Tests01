
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SubjectService;
import domain.Subject;

@Controller
@RequestMapping("/subject")
public class SubjectController extends AbstractController {

	@Autowired
	private SubjectService	subjectService;


	public SubjectController() {
		super();
	}

	// Listing ---------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(defaultValue = "") final String keyword, final String sw) {
		ModelAndView view;

		Collection<Subject> subjects;

		view = new ModelAndView("subject/list");

		if (sw.equals("sin"))
			subjects = this.subjectService.findSubjectsByWordWithoutSeats(keyword);
		else
			subjects = this.subjectService.findSubjectsByWordWithSeats(keyword);

		view.addObject("subjects", subjects);
		view.addObject("requestURI", "subjects/list.do");

		return view;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView view;

		Collection<Subject> subjects;

		view = new ModelAndView("subject/list");

		subjects = this.subjectService.findAll();
		view.addObject("subjects", subjects);
		view.addObject("requestURI", "subjects/list.do");
		return view;
	}
}
