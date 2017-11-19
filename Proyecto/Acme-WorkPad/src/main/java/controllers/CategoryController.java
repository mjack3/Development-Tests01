
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Category;
import domain.School;
import services.CategoryService;
import services.SchoolService;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	@Autowired
	private CategoryService	categoryService;
	@Autowired
	private SchoolService	schoolService;


	@RequestMapping("/view")
	public ModelAndView view(@RequestParam final Category q) {

		try {
			Assert.isTrue(categoryService.exists(q.getId()));
			ModelAndView res;
			res = new ModelAndView("category/view");
			School school = schoolService.findAll().iterator().next();
			res.addObject("image", school.getBanner());
			res.addObject("category", q);
			return res;
		} catch (Throwable e) {
			ModelAndView res = new ModelAndView("redirect:/welcome/index.do");
			return res;

		}

	}

}
