
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

import domain.Actor;
import domain.Folder;
import domain.MailMessage;
import security.LoginService;
import services.FolderService;
import services.MailMessageService;

@Controller
@RequestMapping("/mailmessage")
public class MailMessageController {

	@Autowired
	MailMessageService	mailmessageService;
	@Autowired
	FolderService		folderService;
	@Autowired
	LoginService		loginService;


	@RequestMapping(value = "/actor/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) String error) {
		ModelAndView result;

		result = createNewModelAndView(mailmessageService.create(), null);
		result.addObject("error", error);

		return result;
	}

	@RequestMapping(value = "/actor/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@RequestParam String subject, @RequestParam String body, @RequestParam String priority, @RequestParam String mail) {

		try {
			mailmessageService.send(subject, body, priority, mail);
		} catch (Exception e) {
			return create(e.getMessage());
		}

		return new ModelAndView("redirect:/folder/actor/list.do");
	}

	protected ModelAndView createNewModelAndView(MailMessage mailmessage, String message) {
		ModelAndView result;
		result = new ModelAndView("mailmessage/create");
		result.addObject("mailmessage", mailmessage);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/actor/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, @RequestParam Folder folder) {
		ModelAndView result;

		request.getSession().setAttribute("folder_id", folder.getId());
		result = new ModelAndView("mailmessage/list");
		result.addObject("mailmessage", folder.getMessages());

		return result;
	}

	@RequestMapping(value = "/actor/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam MailMessage q) {
		ModelAndView result;
		result = new ModelAndView("mailmessage/view");
		result.addObject("mailmessage", q);
		return result;
	}

	@RequestMapping(value = "/actor/move", method = RequestMethod.GET)
	public ModelAndView move(HttpServletRequest request, @RequestParam MailMessage m, @RequestParam Folder f) {

		mailmessageService.moveTo(m, f);

		return list(request, f);
	}

	@RequestMapping(value = "/actor/moveTo", method = RequestMethod.GET)
	public ModelAndView moveTo(@RequestParam MailMessage q) {
		ModelAndView result;
		Actor a = loginService.selectSelf();
		result = new ModelAndView("mailmessage/moveTo");
		result.addObject("mailmessage", q);
		result.addObject("folders", a.getFolders());
		return result;
	}

	@RequestMapping(value = "/actor/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam MailMessage mailmessage) {
		ModelAndView result;
		result = new ModelAndView("mailmessage/edit");
		result.addObject("mailmessage", mailmessage);
		return result;
	}

	@RequestMapping(value = "/actor/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid MailMessage mailmessage) {
		ModelAndView result;

		try {
			mailmessageService.delete(mailmessage);
			result = new ModelAndView("redirect:/mailmessage/list.do");
		} catch (Throwable th) {
			result = createEditModelAndView(mailmessage, "mailmessage.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/actor/delete", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request, @RequestParam MailMessage q) {
		ModelAndView result;

		int folder_id = (int) request.getSession().getAttribute("folder_id");

		try {
			mailmessageService.delete(q);
			request.getSession().removeAttribute("folder_id");
			result = list(request, folderService.findOne(folder_id));
		} catch (Throwable th) {
			result = list(request, folderService.findOne(folder_id));
		}

		return result;
	}

	@RequestMapping(value = "/actor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid MailMessage mailmessage, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(mailmessage, null);
		} else {
			try {
				mailmessageService.save(mailmessage);
				result = new ModelAndView("redirect:/mailmessage/list.do");
			} catch (Throwable th) {
				result = createEditModelAndView(mailmessage, "mailmessage.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(MailMessage mailmessage, String message) {
		ModelAndView result = new ModelAndView("mailmessage/edit");

		result.addObject("mailmessage", mailmessage);
		result.addObject("message", message);

		return result;
	}

}
