package com.ihome;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ihome.data.HeatingRepository;
import com.ihome.node.HeatingSettings;
import com.ihome.node.ZoneMode;
import com.ihome.node.ZoneSetting;
import com.ihome.node.ZoneTimerEntry;

// SessionAttributes read here: 
// http://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/spring-model-attribute-with-session/

@Controller
@SessionAttributes("heatingSettings")
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

//	@RequestMapping("/")
//	public ModelAndView main() {
//		HeatingSettings heatingSettings = repo.getSettings(0); // TODO: Change to handle multiple devices
//	
//		ModelAndView modelAndView = new ModelAndView("main");
//		modelAndView.addObject("heatingSettings", heatingSettings);
//		return modelAndView;
//		
//	}

	@RequestMapping("/")
	public String main() {
		return "redirect:/devices/0";
		
	}
	
	@RequestMapping("/devices/{device}")
	public ModelAndView device(@PathVariable int device) {
		HeatingSettings heatingSettings = repo.getSettings(device); // TODO: Change to handle multiple devices
	
		ModelAndView modelAndView = new ModelAndView("main");
		modelAndView.addObject("device", device);
		modelAndView.addObject("heatingSettings", heatingSettings);
		return modelAndView;
	}
	
	
	// TODO: Only temporary function;
	@RequestMapping(path="/addzonetimerentry/{device}/{zone}", method=RequestMethod.GET)
	public ModelAndView addZoneSettingsGet(@PathVariable int device, @PathVariable int zone) {
		System.out.println("GET");
		
		// TODO: Check if zone exists first.
	
		ModelAndView modelAndView = new ModelAndView("addzonetimerentry");
		modelAndView.addObject("zoneTimerEntry", ZoneTimerEntry.createRandom());
		modelAndView.addObject("heatingSettings", repo.getSettings(device));
		modelAndView.addObject("zone", zone);
		modelAndView.addObject("device", device);
		return modelAndView;
	}
	
	@RequestMapping(path="/addzonetimerentry/{device}/{zone}", method=RequestMethod.POST)
	public String addZoneSettingsPost(@ModelAttribute("heatingSettings") HeatingSettings heatingSettings, 
			@PathVariable int device, 
			@PathVariable int zone,
			ZoneTimerEntry z) {
		System.out.println("POST");
	
		//ModelAndView modelAndView = new ModelAndView("addzonetimerentry");
		
		if (!heatingSettings.equals(repo.getSettings(device))) {
			throw new IllegalArgumentException("heatingSettings in session and in the DB are different!");
		}
		
		// TODO: Saving properly.
		List<ZoneSetting> zones = heatingSettings.getZones();
		ZoneSetting zoneSetting = zones.get(zone); //.getAutomaticModeSettings();
		//automaticModeSettings zoneSetting.getAutomaticModeSettings();
		Set<ZoneTimerEntry> entries = zoneSetting.getAutomaticModeSettings();
		Set<ZoneTimerEntry> newEntries = new HashSet<>(entries);
		newEntries.add(z);
		
		zoneSetting.setAutomaticModeSettings(newEntries);
		zones.set(zone, zoneSetting);
		heatingSettings.setZones(zones);
		repo.setSettings(device, heatingSettings);
		
		return "redirect:/";
		
		//return modelAndView;
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
