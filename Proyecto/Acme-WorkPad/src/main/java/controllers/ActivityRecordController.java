
package controllers;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityRecordService;
import domain.ActivityRecord;

@Controller
@RequestMapping("/activityRecord/authenticated")
public class ActivityRecordController extends AbstractController {

	public ActivityRecordController() {
		super();
	}


	@Autowired
	private ActivityRecordService	activityRecordService;


	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView resul = new ModelAndView("activityRecord/list");

		final List<ActivityRecord> activityRecords = this.activityRecordService.findAllPrincipal();

		resul.addObject("activityRecords", activityRecords);
		resul.addObject("requestURI", "activityRecord/authenticated/list.do");
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
	public ModelAndView save(ActivityRecord activityRecord, final BindingResult bindingResult) {
		ModelAndView resul;

		try {

			activityRecord = this.activityRecordService.reconstruct(activityRecord, bindingResult);

			final Date tomorrow = DateUtils.addDays(new Date(), 1);
			if (activityRecord.getWrittenDate().after(tomorrow)) {
				bindingResult.rejectValue("writtenDate", "must.be.past", "must be past");
				throw new IllegalArgumentException();
			}

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

	private ModelAndView createEditModelAndView(final ActivityRecord activityRecord, final String message) {
		// TODO Auto-generated method stub
		final ModelAndView resul = new ModelAndView("activityRecord/edit");

		resul.addObject("activityRecord", activityRecord);
		resul.addObject("message", message);
		resul.addObject("actionParam", "activityRecord/authenticated/edit.do");

		return resul;
	}

}
