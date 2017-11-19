
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.School;
import services.BibliographyRecordService;
import services.SchoolService;
import services.SubjectService;

@Controller
@RequestMapping("/bibliographyrecord")
public class BibliographyRecordController extends AbstractController {

	@Autowired
	private BibliographyRecordService	bibliographyRecordService;
	@Autowired
	private SubjectService				subjectService;
	@Autowired
	private SchoolService				schoolService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int q) {
		ModelAndView result;

		try {
			result = new ModelAndView("bibliographyrecord/list");
			School school = schoolService.findAll().iterator().next();
			result.addObject("image", school.getBanner());

			result.addObject("bibliographyrecord", subjectService.findOne(q).getBibliographiesRecords());
		} catch (Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

}
