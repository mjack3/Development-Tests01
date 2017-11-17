
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BibliographyRecordService;
import services.SubjectService;

@Controller
@RequestMapping("/bibliographyrecord")
public class BibliographyRecordController extends AbstractController {

	@Autowired
	private BibliographyRecordService	bibliographyRecordService;
	@Autowired
	private SubjectService				subjectService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int q) {
		ModelAndView result;

		result = new ModelAndView("bibliographyrecord/list");

		result.addObject("bibliographyrecord", subjectService.findOne(q).getBibliographiesRecords());

		return result;
	}

}
