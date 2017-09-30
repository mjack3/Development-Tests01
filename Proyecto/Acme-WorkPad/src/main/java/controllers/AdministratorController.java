
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import domain.Subject;
import security.LoginService;
import services.AdministratorService;
import services.SubjectService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	SubjectService			subjectService;

	@Autowired
	LoginService			loginService;


	//Mis asignaturas

	@RequestMapping(value = "/subject/list", method = RequestMethod.GET)
	public ModelAndView listAdministrator() {
		ModelAndView result;

		final Administrator a = (Administrator) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		result = new ModelAndView("subject/list");
		result.addObject("var", 2);
		final List<Subject> subjectByAdministrator = new ArrayList<Subject>();
		if (this.administratorService.exists(a.getId()))
			for (final Subject sub : a.getSubjects())
				subjectByAdministrator.add(sub);

		result.addObject("subjects", subjectByAdministrator);

		return result;
	}

}
