package com.ihome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ihome.data.HeatingRepository;
import com.ihome.node.HeatingSettings;
import com.ihome.node.ZoneMode;
import com.ihome.node.ZoneSetting;
import com.ihome.node.ZoneTimerEntry;

@Controller
public class MainController {
	
	HeatingRepository repo;
//	private static final Logger logger = LoggerFactory.getLogger(ControllerConfig.class); 
	
	@Autowired
	public MainController(HeatingRepository repo) {
		this.repo = repo;
	}
	
	
//	@ExceptionHandler()
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public void handle(HttpMessageNotReadableException e) {
//		logger.warn("Returning HTTP 400 Bad request", e);
//		throw e;
//	}	

	@RequestMapping("/")
	public ModelAndView main() {
		HeatingSettings heatingSettings = repo.getSettings(0); // TODO: Change to handle multiple devices
	
		ModelAndView modelAndView = new ModelAndView("main");
		modelAndView.addObject("heatingSettings", heatingSettings);
		return modelAndView;
	}
	
	// TODO: Only temporary function;
	@RequestMapping(path="/addzonetimerentry", method=RequestMethod.GET)
	public ModelAndView addZoneSettingsGet() {
		System.out.println("GET");
		//HeatingSettings heatingSettings = repo.getSettings(0);
	
		ModelAndView modelAndView = new ModelAndView("addzonetimerentry");
		modelAndView.addObject("zoneTimerEntry", ZoneTimerEntry.createRandom());
		return modelAndView;
	}
	
	@RequestMapping(path="/addzonetimerentry", method=RequestMethod.POST)
	public ModelAndView addZoneSettingsPost(ZoneTimerEntry z) {
		System.out.println("POST");
		//HeatingSettings heatingSettings = repo.getSettings(0);
	
		ModelAndView modelAndView = new ModelAndView("addzonetimerentry");
		modelAndView.addObject("zoneTimerEntry", z);
		
		return modelAndView;
	}	
	
	
	@RequestMapping("/setmanual/{device}/{zone}/{on}")
	public ModelAndView manualSetOn(@PathVariable int device, @PathVariable int zone, @PathVariable int on) {
		HeatingSettings heatingSettings = repo.getSettings(device); // This may throw an excepiton -> e.g. invalid device
		if (heatingSettings.getZones().size() < zone)
			throw new IllegalArgumentException("Invalid zone;"); // TODO: Create ean exception
		
		if (on != 0 && on != 1)
			throw new IllegalArgumentException("Invalid on/off status;"); // TODO: Create ean exception
		
		heatingSettings.getZones().get(zone).setManualModeSetting( (on == 1) );
		heatingSettings.getZones().get(zone).setMode(ZoneMode.MANUAL);
		System.out.println("Saving:" + heatingSettings);
		repo.setSettings(device, heatingSettings);
		
		ModelAndView modelAndView = new ModelAndView("main");
		modelAndView.addObject("heatingSettings", heatingSettings);
		return modelAndView;
	}
	
}
