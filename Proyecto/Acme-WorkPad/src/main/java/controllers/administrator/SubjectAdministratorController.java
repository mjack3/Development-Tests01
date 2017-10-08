
package controllers.administrator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Administrator;
import domain.Category;
import domain.Subject;
import domain.Teacher;
import security.LoginService;
import services.AdministratorService;
import services.CategoryService;
import services.SubjectService;
import services.TeacherService;

@Controller
@RequestMapping("/subject/administrator")
public class SubjectAdministratorController extends AbstractController {

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	SubjectService			subjectService;

	@Autowired
	LoginService			loginService;

	@Autowired
	CategoryService			categoryService;

	@Autowired
	TeacherService			teacherService;

	private Subject tosave;


	//Mis asignaturas

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAdministrator() {
		ModelAndView result;

		final Administrator a = (Administrator) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		result = new ModelAndView("subject/list");
		result.addObject("requestURI", "/subject/administrator/list.do");
		final List<Subject> subjectByAdministrator = new ArrayList<Subject>();
		if (this.administratorService.exists(a.getId()))
			for (final Subject sub : a.getSubjects())
				subjectByAdministrator.add(sub);

		result.addObject("subject", subjectByAdministrator);
		result.addObject("requestSearch", "subject/authenticated/search.do");
		result.addObject("MySubjects", 1);

		return result;
	}

	@RequestMapping(value = "/associateTeacher", method = RequestMethod.GET)
	public ModelAndView associateTeacher(@RequestParam Integer q) {
		ModelAndView result;
		tosave= subjectService.findOne(q);
		result= new ModelAndView("subject/associate");
		result.addObject("teachers",teacherService.findAll());


		return result;
	}

	@RequestMapping(value = "/saveAssociate", method = RequestMethod.GET)
	public ModelAndView saveAssociate(@RequestParam Integer q) {
		ModelAndView result;
		Administrator a = (Administrator) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Teacher teacher = teacherService.findOne(q);	
		try {	
			Subject subject = tosave;
			subject.setTeacher(teacher);
			subject = this.subjectService.save(subject);
			List<Subject> subs = teacher.getSubjects();
			subs.add(subject);
			teacher.setSubjects(subs);
			teacherService.save(teacher);
			result = new ModelAndView("redirect:/subject/administrator/list.do");
			result.addObject("subject", a.getSubjects());
		} catch (Throwable th) {
			th.printStackTrace();
			result = new ModelAndView("redirect:/subject/administrator/list.do");
			result.addObject("subject", a.getSubjects());
		}
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer q) {
		ModelAndView result;
		result = new ModelAndView("subject/edit");
		result.addObject("subject", this.subjectService.findOne(q));
		final List<Category> categories = this.categoryService.findAll();
		result.addObject("categories", categories);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit( @Valid Subject subject, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(subject, null);
		} else {	
			try {
				this.subjectService.save(subject);
				
				result = new ModelAndView("redirect:/subject/administrator/list.do");

			} catch (Throwable th) {
				th.printStackTrace();
				result = this.createEditModelAndView(subject, "subject.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam Subject q) {
		ModelAndView result;

		try {
			final List<Category> categories = this.categoryService.findAll();
			this.subjectService.delete(q);
			result = new ModelAndView("redirect:/subject/administrator/list.do");
			result.addObject("categories", categories);
		} catch (final Throwable th) {
			th.printStackTrace();
			result = new ModelAndView("redirect:/subject/administrator/list.do");
			System.out.print(q);
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Subject subject) {
		ModelAndView result;

		result = this.createEditModelAndView(subject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Subject subject, final String message) {
		ModelAndView result;

		result = new ModelAndView("subject/edit");
		List<Category> categories = this.categoryService.findAll();
		result.addObject("categories", categories);
		result.addObject("subject", subject);
		result.addObject("message", message);

		return result;
	}

}
