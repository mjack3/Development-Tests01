
package controllers;

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

import security.LoginService;
import services.FolderService;
import services.SchoolService;
import domain.Actor;
import domain.Folder;
import domain.School;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController {

	@Autowired
	private FolderService	folderService;
	@Autowired
	private LoginService	loginService;
	@Autowired
	private SchoolService	schoolService;


	@RequestMapping(value = "/actor/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = this.createNewModelAndView(this.folderService.create(), null);

		return result;
	}

	@RequestMapping(value = "/actor/createSubFolder", method = RequestMethod.GET)
	public ModelAndView createSubFolder(@RequestParam final Folder folder) {
		ModelAndView result;

		final Folder f = this.folderService.create();
		f.setFolderFather(folder);
		result = this.createNewModelAndView(f, null);

		return result;
	}

	@RequestMapping(value = "/actor/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createNewModelAndView(folder, null);
		else
			try {
				final Folder saved = this.folderService.saveCreate(folder);
				//Añadimos la carpeta creada a la lista de hijos de la carpeta padre si no está añadida
				if (null != saved.getFolderFather() && !saved.getFolderFather().getFolderChildren().contains(folder)) {
					saved.getFolderFather().getFolderChildren().add(saved);
					this.folderService.save(saved.getFolderFather());
				}

				result = new ModelAndView("redirect:/folder/actor/list.do");
			} catch (final Throwable th) {
				result = this.createNewModelAndView(folder, "folder.commit.error");
			}
		return result;
	}

	protected ModelAndView createNewModelAndView(final Folder folder, final String message) {
		ModelAndView result;
		result = new ModelAndView("folder/create");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("folder", folder);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/actor/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Folder folder) {
		ModelAndView result;
		final Actor a = this.loginService.selectSelf();
		if (a != null) {
			if (a.getFolders().contains(folder) && folder.getFolderChildren().isEmpty()) {
				this.folderService.delete(folder);
				result = new ModelAndView("folder/list");
				final School school = this.schoolService.findAll().iterator().next();
				result.addObject("image", school.getBanner());
			} else {
				result = new ModelAndView("folder/list");
				final School school = this.schoolService.findAll().iterator().next();
				result.addObject("image", school.getBanner());
			}

			final List<Folder> folders = new ArrayList<Folder>();
			for (final Folder f : a.getFolders())
				if (null == f.getFolderFather())
					folders.add(f);
			result.addObject("folder", folders);
		} else
			return new ModelAndView("redirect:/welcome/index.do");
		return result;
	}

	@RequestMapping(value = "/actor/deleteSubFolder", method = RequestMethod.GET)
	public ModelAndView deleteSubFolder(@RequestParam final Folder folder) {
		ModelAndView result;
		final Actor a = this.loginService.selectSelf();
		if (a != null) {
			if (this.folderService.findAllFolder(a.getId()).contains(folder) && folder.getFolderChildren().isEmpty()) {

				//Eliminamos primeramente la carpeta de la carpeta padre y modificamos en bbdd
				folder.getFolderFather().getFolderChildren().remove(folder);
				this.folderService.save(folder.getFolderFather());

				//A continuación eliminamos la folder actual
				this.folderService.delete(folder);
				result = new ModelAndView("folder/list");

				final School school = this.schoolService.findAll().iterator().next();
				result.addObject("image", school.getBanner());
			} else {
				result = new ModelAndView("folder/list");
				final School school = this.schoolService.findAll().iterator().next();
				result.addObject("image", school.getBanner());
			}

			final List<Folder> folders = new ArrayList<Folder>();
			for (final Folder f : a.getFolders())
				if (null == f.getFolderFather())
					folders.add(f);
			result.addObject("folder", folders);

		} else
			return new ModelAndView("redirect:/welcome/index.do");
		return result;
	}

	@RequestMapping(value = "/actor/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Actor a = this.loginService.selectSelf();

		result = new ModelAndView("folder/list");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());

		final List<Folder> folders = new ArrayList<Folder>();
		for (final Folder f : a.getFolders())
			if (null == f.getFolderFather())
				folders.add(f);
		result.addObject("folder", folders);

		return result;
	}

	@RequestMapping(value = "/actor/listFolder", method = RequestMethod.GET)
	public ModelAndView listFolder(@RequestParam final Folder folder) {
		ModelAndView result;

		//		final Folder folder = this.folderService.findOne(id);

		result = new ModelAndView("folder/listFolder");

		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("folder", folder);

		return result;
	}

	@RequestMapping(value = "/actor/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Folder folder) {
		ModelAndView result;
		final Actor a = this.loginService.selectSelf();
		if (a != null) {
			if (a.getFolders().contains(folder)) {
				result = new ModelAndView("folder/edit");
				final School school = this.schoolService.findAll().iterator().next();
				result.addObject("image", school.getBanner());
				result.addObject("folder", folder);
			} else {
				result = new ModelAndView("folder/list");
				final School school = this.schoolService.findAll().iterator().next();
				result.addObject("image", school.getBanner());
				result.addObject("folder", a.getFolders());
			}
		} else
			return new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/actor/editSubFolder", method = RequestMethod.GET)
	public ModelAndView editSubFolder(@RequestParam final Folder folder) {
		ModelAndView result;
		final Actor a = this.loginService.selectSelf();
		if (a != null) {

			if (this.folderService.findAllFolder(a.getId()).contains(folder)) {
				result = new ModelAndView("folder/edit");

				final School school = this.schoolService.findAll().iterator().next();
				result.addObject("image", school.getBanner());

				result.addObject("folder", folder);
			} else {
				result = new ModelAndView("folder/list");
				final School school = this.schoolService.findAll().iterator().next();
				result.addObject("image", school.getBanner());
				result.addObject("folder", a.getFolders());

			}
		} else
			return new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/actor/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid final Folder folder) {
		ModelAndView result;

		try {
			this.folderService.delete(folder);
			result = new ModelAndView("redirect:/folder/actor/list.do");
		} catch (final Throwable th) {
			result = this.createEditModelAndView(folder, "folder.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/actor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(folder, null);
		else
			try {
				this.folderService.save(folder);
				result = new ModelAndView("redirect:/folder/actor/list.do");
			} catch (final Throwable th) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String message) {
		final ModelAndView result = new ModelAndView("folder/edit");
		final School school = this.schoolService.findAll().iterator().next();
		result.addObject("image", school.getBanner());
		result.addObject("folder", folder);
		result.addObject("message", message);

		return result;
	}

}
