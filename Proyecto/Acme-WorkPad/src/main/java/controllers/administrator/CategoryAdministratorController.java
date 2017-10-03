
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Category;
import services.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	CategoryService categoryService;


	@RequestMapping("/view")
	public ModelAndView view(@RequestParam final Category q) {
		ModelAndView res;

		res = new ModelAndView("category/view");
		res.addObject("category", q);

		return res;
	}

}
