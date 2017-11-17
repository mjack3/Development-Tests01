
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.School;
import domain.SpamWord;
import services.SchoolService;
import services.SpamWordService;

@Controller
@RequestMapping("/spamword")
public class SpamWordController extends AbstractController {

	@Autowired
	private SpamWordService	spamwordService;
	@Autowired
	private SchoolService	schoolService;


	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = createNewModelAndView(spamwordService.create(), null);

		return result;
	}

	@RequestMapping(value = "/administrator/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid SpamWord spamword, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(spamword, null);
		} else {
			try {
				spamwordService.save(spamword);
				result = new ModelAndView("redirect:/spamword/administrator/list.do");
			} catch (Throwable th) {
				result = createEditModelAndView(spamword, "spamword.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(SpamWord spamword, String message) {
		ModelAndView result;
		result = new ModelAndView("spamword/create");
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("spamword", spamword);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("spamword/list");
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("spamword", spamwordService.findAll());

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam SpamWord q) {
		ModelAndView result;
		result = new ModelAndView("spamword/edit");
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("spamword", q);
		return result;
	}

	@RequestMapping(value = "/administrator/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam SpamWord q) {
		ModelAndView result;

		spamwordService.delete(q);
		result = new ModelAndView("redirect:/spamword/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/administrator/save-edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid SpamWord spamword, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(spamword, null);
		} else {
			try {
				spamwordService.save(spamword);
				result = new ModelAndView("redirect:/spamword/administrator/list.do");
			} catch (Throwable th) {
				result = createEditModelAndView(spamword, "spamword.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(SpamWord spamword, String message) {
		ModelAndView result = new ModelAndView("spamword/edit");
		School school = schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());

		result.addObject("spamword", spamword);
		result.addObject("message", message);

		return result;
	}

}
