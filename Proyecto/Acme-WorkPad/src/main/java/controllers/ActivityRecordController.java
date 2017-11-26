
package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityRecordService;
import services.ActorService;
import services.SchoolService;
import domain.ActivityRecord;
import domain.Actor;
import domain.School;

@Controller
@RequestMapping("/activityRecord/authenticated")
public class ActivityRecordController extends AbstractController {

	public ActivityRecordController() {
		super();
	}


	@Autowired
	private ActivityRecordService	activityRecordService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SchoolService			schoolService;


	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "0") int userAccountId) {
		final ModelAndView resul = new ModelAndView("activityRecord/list");
		final School school = this.schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());
		final List<ActivityRecord> activityRecord;
		if (userAccountId != 0)
			activityRecord = this.activityRecordService.findAllPrincipal();
		else {
			userAccountId = this.actorService.findOnePrincipal().getUserAccount().getId();
			activityRecord = this.activityRecordService.findAllByUserAccountId(userAccountId);
		}
		final Actor principal = this.actorService.findOnePrincipal();

		resul.addObject("activityRecord", activityRecord);
		resul.addObject("principal", principal);
		resul.addObject("userAccountId", userAccountId);
		resul.addObject("requestURI", "activityRecord/authenticated/list.do");
		return resul;
	}

	@RequestMapping(value = "/listSystem.do", method = RequestMethod.GET)
	public ModelAndView listSystem() {
		final ModelAndView resul = new ModelAndView("activityRecord/listSystem");

		final Collection<ActivityRecord> activityRecords = this.activityRecordService.findAllSystemByPrincipal();
		resul.addObject("activityRecords", activityRecords);
		resul.addObject("requestURI", "activityRecord/authenticated/listSystem.do");

		final School school = this.schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());

		return resul;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView resul;
		final ActivityRecord activityRecord = this.activityRecordService.create();
		resul = this.createEditModelAndView(activityRecord, null);
		return resul;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int q) {

		final ActivityRecord activityRecord = this.activityRecordService.findOnePrincipal(q);
		final ModelAndView resul = this.createEditModelAndView(activityRecord, null);

		return resul;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ActivityRecord activityRecord, final BindingResult bindingResult) {
		ModelAndView resul;
		if (bindingResult.hasErrors())
			resul = this.createEditModelAndView(activityRecord, null);
		else

			try

			{

				activityRecord = this.activityRecordService.reconstruct(activityRecord, bindingResult);

				if (bindingResult.hasErrors())
					resul = this.createEditModelAndView(activityRecord, null);
				else {
					this.activityRecordService.save(activityRecord);
					resul = new ModelAndView("redirect:list.do");
				}

			} catch (final Throwable oops) {
				// TODO: handle exception
				resul = this.createEditModelAndView(activityRecord, "activityRecord.commit.error");
			}

		return resul;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int q) {
		ModelAndView resul;

		try {

			final ActivityRecord activityRecord = this.activityRecordService.findOnePrincipal(q);
			this.activityRecordService.delete(activityRecord);
			resul = new ModelAndView("redirect:list.do");

		} catch (final Throwable oops) {
			resul = new ModelAndView("redirect:list");
			resul.addObject("message", "activityRecord.commit.error");
		}

		return resul;
	}

	private ModelAndView createEditModelAndView(final ActivityRecord activityRecord, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("activityRecord/edit");
		final School school = this.schoolService.findAll().iterator().next();
		resul.addObject("image", school.getBanner());
		resul.addObject("activityRecord", activityRecord);
		resul.addObject("message", message);
		resul.addObject("actionParam", "activityRecord/authenticated/edit.do");

		return resul;
	}

}
