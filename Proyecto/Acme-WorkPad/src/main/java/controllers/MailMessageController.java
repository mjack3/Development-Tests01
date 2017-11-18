
package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.FolderService;
import services.MailMessageService;
import services.SchoolService;
import domain.Actor;
import domain.Folder;
import domain.MailMessage;
import domain.School;

@Controller
@RequestMapping("/mailmessage")
public class MailMessageController extends AbstractController {

	@Autowired
	private MailMessageService	mailmessageService;
	@Autowired
	private FolderService		folderService;
	@Autowired
	private LoginService		loginService;
	@Autowired
	private SchoolService		schoolService;


	@RequestMapping(value = "/actor/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) final String error) {
		ModelAndView result;

		result = this.createNewModelAndView(this.mailmessageService.create(), null);
		result.addObject("error", error);

		return result;
	}

	@RequestMapping(value = "/actor/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@RequestParam final String subject, @RequestParam final String body, @RequestParam final String priority, @RequestParam final String mail) {
		ModelAndView result;
		if (subject == null || subject == "" || body == null || body == "")
			result = this.createNewModelAndView(this.mailmessageService.create(), "commit.error");
		else
			try {
				this.mailmessageService.send(subject, body, priority, mail);
				result = new ModelAndView("redirect:/folder/actor/list.do");
			} catch (final Exception e) {
				return this.create(e.getMessage());
			}

		return result;
	}

	protected ModelAndView createNewModelAndView(final MailMessage mailmessage, final String message) {
		ModelAndView result;
		result = new ModelAndView("mailmessage/create");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("mailmessage", mailmessage);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/actor/list", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request, @RequestParam final Folder folder) {
		ModelAndView result;

		request.getSession().setAttribute("folder_id", folder.getId());
		result = new ModelAndView("mailmessage/list");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("mailmessage", folder.getMessages());

		return result;
	}

	@RequestMapping(value = "/actor/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final MailMessage q) {
		ModelAndView result;
		result = new ModelAndView("mailmessage/view");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("mailmessage", q);
		return result;
	}

	@RequestMapping(value = "/actor/move", method = RequestMethod.GET)
	public ModelAndView move(final HttpServletRequest request, @RequestParam final MailMessage m, @RequestParam final Folder f) {

		this.mailmessageService.moveTo(m, f);

		return this.list(request, f);
	}

	@RequestMapping(value = "/actor/moveTo", method = RequestMethod.GET)
	public ModelAndView moveTo(@RequestParam final MailMessage q) {
		ModelAndView result;
		final Actor a = this.loginService.selectSelf();
		result = new ModelAndView("mailmessage/moveTo");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("mailmessage", q);
		result.addObject("folders", a.getFolders());
		return result;
	}

	@RequestMapping(value = "/actor/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final MailMessage mailmessage) {
		ModelAndView result;
		result = new ModelAndView("mailmessage/edit");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("mailmessage", mailmessage);
		return result;
	}

	@RequestMapping(value = "/actor/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid final MailMessage mailmessage) {
		ModelAndView result;

		try {
			this.mailmessageService.delete(mailmessage);
			result = new ModelAndView("redirect:/mailmessage/list.do");
		} catch (final Throwable th) {
			result = this.createEditModelAndView(mailmessage, "mailmessage.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/actor/delete", method = RequestMethod.GET)
	public ModelAndView delete(final HttpServletRequest request, @RequestParam final MailMessage q) {
		ModelAndView result;

		final int folder_id = (int) request.getSession().getAttribute("folder_id");

		try {
			this.mailmessageService.delete(q);
			request.getSession().removeAttribute("folder_id");
			result = this.list(request, this.folderService.findOne(folder_id));
		} catch (final Throwable th) {
			result = this.list(request, this.folderService.findOne(folder_id));
		}

		return result;
	}

	@RequestMapping(value = "/actor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final MailMessage mailmessage, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(mailmessage, null);
		else
			try {
				this.mailmessageService.save(mailmessage);
				result = new ModelAndView("redirect:/mailmessage/list.do");
			} catch (final Throwable th) {
				result = this.createEditModelAndView(mailmessage, "mailmessage.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final MailMessage mailmessage, final String message) {
		final ModelAndView result = new ModelAndView("mailmessage/edit");

		result.addObject("mailmessage", mailmessage);
		result.addObject("message", message);

		return result;
	}

}
