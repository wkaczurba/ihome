package com.ihome;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ihome.data.HeatingReadbackRepository;
import com.ihome.data.HeatingRepository;
import com.ihome.node.HeatingSettings;
import com.ihome.node.ZoneMode;
import com.ihome.node.ZoneSetting;
import com.ihome.node.ZoneTimerEntry;

// SessionAttributes read here: 
// http://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/spring-model-attribute-with-session/

@Controller
public class MainController {
	
	HeatingRepository repo;
	HeatingReadbackRepository readbackRepo;
//	private static final Logger logger = LoggerFactory.getLogger(ControllerConfig.class); 
	
	@Autowired
	public MainController(HeatingRepository repo, HeatingReadbackRepository readbackRepo) {
		this.repo = repo;
		this.readbackRepo = readbackRepo;
		
		if (repo.findAll().stream().anyMatch(x -> x.getDeviceId() == 0)) {			
			return;
		} else {
			//repo.deleteAll();
			repo.createInitial();
		}
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
	
	@RequestMapping("/createrandom")
	public ModelAndView createRandom() {
		HeatingSettings hs = HeatingSettings.createRandom(0);
		repo.save(hs);
		
		ModelAndView modelAndView = new ModelAndView("createrandom");
		modelAndView.addObject("heatingSettings", hs);
		return modelAndView;
	}
	
	@RequestMapping("/create")
	public ModelAndView create() {
		ZoneSetting zs0 = new ZoneSetting(ZoneMode.MANUAL_ON/*, true*/);
		ZoneSetting zs1 = new ZoneSetting(ZoneMode.MANUAL_ON/*, true*/);
		ZoneSetting zs2 = new ZoneSetting(ZoneMode.MANUAL_ON/*, true*/);
		zs0.setAutomaticModeSettings(new HashSet<>(Arrays.asList(new ZoneTimerEntry(LocalTime.of(7, 00), LocalTime.of(5, 0), new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)), new HashSet<>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)) ))));
		zs1.setAutomaticModeSettings(new HashSet<>(Arrays.asList(new ZoneTimerEntry(LocalTime.of(7, 00), LocalTime.of(5, 0), new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)), new HashSet<>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)) ))));
		zs2.setAutomaticModeSettings(new HashSet<>(Arrays.asList(new ZoneTimerEntry(LocalTime.of(7, 00), LocalTime.of(5, 0), new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)), new HashSet<>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)) ))));
		//HeatingSettings hs = new HeatingSettings(new ArrayList<>(Arrays.asList(zs0, zs1, zs2))); // TODO: Remove.

		HeatingSettings hs = new HeatingSettings(0, zs0, zs1, zs2);
//		hs.setDeviceId(0);
//		hs.addZone(zs0);
//		hs.addZone(zs1);
//		hs.addZone(zs2);
		
		repo.save(hs);		

		ModelAndView modelAndView = new ModelAndView("create");
		modelAndView.addObject("heatingSettings", hs);
		return modelAndView;
	}
	
	@RequestMapping("/test1")
	//@Test
	public String hibernateTst() {
		// create entries first.
		
		ZoneSetting zs0 = new ZoneSetting(ZoneMode.MANUAL_ON/*, true*/);
		ZoneSetting zs1 = new ZoneSetting(ZoneMode.MANUAL_ON/*, true*/);
		ZoneSetting zs2 = new ZoneSetting(ZoneMode.MANUAL_ON/*, true*/);
		zs0.setAutomaticModeSettings(new HashSet<>(Arrays.asList(new ZoneTimerEntry(LocalTime.of(7, 00), LocalTime.of(5, 0), new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)), new HashSet<>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)) ))));
		zs1.setAutomaticModeSettings(new HashSet<>(Arrays.asList(new ZoneTimerEntry(LocalTime.of(7, 00), LocalTime.of(5, 0), new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)), new HashSet<>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)) ))));
		zs2.setAutomaticModeSettings(new HashSet<>(Arrays.asList(new ZoneTimerEntry(LocalTime.of(7, 00), LocalTime.of(5, 0), new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)), new HashSet<>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)) ))));
		//HeatingSettings hs = new HeatingSettings(new ArrayList<>(Arrays.asList(zs0, zs1, zs2))); // TODO: Remove.

		HeatingSettings hs = new HeatingSettings(0, zs0, zs1, zs2);
//		hs.addZone(zs0);
//		hs.addZone(zs1);
//		hs.addZone(zs2);
		
		repo.save(hs);		
		if (hs.getZones().size() != 3) {
			throw new RuntimeException("size != 3");
		}
		
		System.out.println(hs);
		//HeatingSettings rb = repo.getOne(hs.getId());
		HeatingSettings rb = repo.getOneByDeviceId(hs.getDeviceId());
		
		
		if (rb.getZones().size() != 3) {
			throw new RuntimeException("size != 3");
		}
					
		ZoneTimerEntry zte = new ZoneTimerEntry(
				LocalTime.of(17, 00), 
				LocalTime.of(23, 0), 
				new HashSet<>(Arrays.asList(DayOfWeek.FRIDAY)), 
				new HashSet<>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY))); 
		rb.getZones().get(0).getAutomaticModeSettings().add(zte);

		//Assert.assertEquals(3, rb.getZones().size());
		if (rb.getZones().size() != 3) {
			throw new RuntimeException("size != 3");
		}
		
		return "null";
	}	

	@RequestMapping("/")
	public String main(Model model) {
		
		List<HeatingSettings> heatingSettingsList = repo.findAll();
		System.out.println(heatingSettingsList);		
		model.addAttribute(heatingSettingsList);
		
		return "main";
		//return "redirect:/devices/1";
	}
	
	@RequestMapping("/devices/{device}")
	public ModelAndView device(@PathVariable long device) {
		//HeatingSettings heatingSettings = repo.findOne(device);
		HeatingSettings heatingSettings = repo.findOneByDeviceId(device);		
		//HeatingSettings heatingSettings = repo.getOne(device);
//		heatingSettings = repo.getOne(device);
	
		ModelAndView modelAndView = new ModelAndView("devices");
		modelAndView.addObject("device", device);
		modelAndView.addObject("heatingSettings", heatingSettings);
		System.out.println("got: " + heatingSettings);
		return modelAndView;
	}
	
	@RequestMapping(path="/switchzone/automatic/{device}/{zone}", method=RequestMethod.GET)
	public String switchZoneToAutomatic(@PathVariable long device, @PathVariable int zone) {
		HeatingSettings hs = repo.findOneByDeviceId(device);
		hs.getZones().get(zone).setMode(ZoneMode.AUTOMATIC);
		repo.save(hs);
		
		return "redirect:/devices/" + device;
	}

	@RequestMapping(path="/switchzone/manualOn/{device}/{zone}", method=RequestMethod.GET)
	public String switchZoneToManualOn(@PathVariable long device, @PathVariable int zone) {
		HeatingSettings hs = repo.findOneByDeviceId(device);
		hs.getZones().get(zone).setMode(ZoneMode.MANUAL_ON);
		repo.save(hs);
		
		return "redirect:/devices/" + device;
	}

	@RequestMapping(path="/switchzone/manualOff/{device}/{zone}", method=RequestMethod.GET)
	public String switchZoneToManualOff(@PathVariable long device, @PathVariable int zone) {
		HeatingSettings hs = repo.findOneByDeviceId(device);
		hs.getZones().get(zone).setMode(ZoneMode.MANUAL_OFF);
		repo.save(hs);
		
		return "redirect:/devices/" + device;
	}

	// TODO: Only temporary function;
	@RequestMapping(path="/addzonetimerentry/{device}/{zone}", method=RequestMethod.GET)
	public ModelAndView addZoneSettingsGet(@PathVariable long device, @PathVariable int zone) {
		System.out.println("GET");
		
		// TODO: Check if zone exists first.
	
		ModelAndView modelAndView = new ModelAndView("addzonetimerentry");
		modelAndView.addObject("zoneTimerEntry", ZoneTimerEntry.createRandom());
//		modelAndView.addObject("heatingSettings", repo.getSettings(device));
//		modelAndView.addObject("heatingSettings", repo.findOne(device));
		modelAndView.addObject("zone", zone);
		modelAndView.addObject("device", device);
		return modelAndView;
	}
	
//	@RequestMapping(path="/deletezonetimerentry/{device}/{zone}/{id}", method=RequestMethod.GET)
//	public String deleteZoneTimerEntry(@PathVariable long device, @PathVariable int zone, @PathVariable int id) {
//		//HeatingSettings hs = repo.findOne(device);
//		HeatingSettings hs = repo.getOne(device);
//		ZoneSetting zs = hs.getZones().get(zone);
//		
//		Set<ZoneTimerEntry> zte = zs.getAutomaticModeSettings();
//		ZoneTimerEntry z = null;
//		try {
//			z = zte.stream().filter(s -> s.getId()==id).findFirst().get(); // throws NoSuchElemenetException
//			zte.remove(z);
//			repo.save(hs);
//		} catch (NoSuchElementException e) {
//			throw new NoSuchElementException("Invalid ZoneTimerEntry id."); 
//		}		
//		return "redirect:/devices/" + device; // TODO: use proper concatenation tool. 
//	}

	@RequestMapping(path="/deletezonetimerentry/{device}/{zone}/{id}", method=RequestMethod.GET)
	@Transactional
	public String deleteZoneTimerEntry(@PathVariable long device, @PathVariable int zone, @PathVariable long id) {
		HeatingSettings hs = repo.findOneByDeviceId(device);
		Set<ZoneTimerEntry> ztes = hs.getZones().get(zone).getAutomaticModeSettings();
		
		try {
			ZoneTimerEntry zte = ztes.stream().filter(x -> x.getId() == id).findAny().get(); // this may throw an error
			System.out.println("Deleting...: " + zte);
			
			if (!hs.getZones().get(zone).getAutomaticModeSettings().remove(zte)) {
				throw new RuntimeException("Element not removed, even though it was found");
			}
			repo.save(hs);
		} catch (NoSuchElementException e) {
			throw e;
		}
		

		/*HeatingSettings hs = repo.getOne(device);
		ZoneSetting zs = hs.getZones().get(zone);
		
		Set<ZoneTimerEntry> zte = zs.getAutomaticModeSettings();
		ZoneTimerEntry z = null;
		try {
			z = zte.stream().filter(s -> s.getId()==id).findFirst().get(); // throws NoSuchElemenetException
			zte.remove(z);
			repo.save(hs);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Invalid ZoneTimerEntry id."); 
		}*/		
		return "redirect:/devices/" + device; // TODO: use proper concatenation tool. 
	}


	@RequestMapping(path="/addzonetimerentry/{device}/{zone}", method=RequestMethod.POST)
	public String addZoneSettingsPost( 
			@PathVariable long device, 
			@PathVariable int zone,
			ZoneTimerEntry z) {
		System.out.println("POST");
	
		//ModelAndView modelAndView = new ModelAndView("addzonetimerentry");
		
		System.out.println("device: " + device);
//		repo.findAll(); // it fails without this one.
		HeatingSettings fromDb = repo.findOneByDeviceId(device);
		
		/*if (!heatingSettings.equals(fromDb)) {
			System.out.println("Session and DB values are different:");
			System.out.println("In session: " + heatingSettings);
			System.out.println("In DB: " + fromDb);

			throw new IllegalArgumentException("heatingSettings in session and in the DB are different!");
		}*/
/*		
		// TODO: Saving properly.
		List<ZoneSetting> zones = heatingSettings.getZones();
		ZoneSetting zoneSetting = zones.get(zone); //.getAutomaticModeSettings();
		//automaticModeSettings zoneSetting.getAutomaticModeSettings();
		Set<ZoneTimerEntry> entries = zoneSetting.getAutomaticModeSettings();
//		Set<ZoneTimerEntry> newEntries = new HashSet<>(entries);
//		newEntries.add(z);
		entries.add(z);
*/
		
		System.out.println("Adding: fromDb.getZones().get("+zone+").getAutomaticModeSettings().add(z);");
		System.out.println("  where z: " + z);
		
		fromDb.getZones().get(zone).getAutomaticModeSettings().add(z);
		repo.save(fromDb);
		
		fromDb = repo.getOneByDeviceId(device);
		System.out.println("fromDb:" + fromDb);

		/*
		zoneSetting.setAutomaticModeSettings(newEntries);
		zones.set(zone, zoneSetting);
		heatingSettings.setZones(zones);
//		repo.setSettings(device, heatingSettings);
 */

//		repo.save(heatingSettings);
		
		return "redirect:/devices/" + device; // TODO: use proper concatenation tool. 
		
		//return modelAndView;
	}		
	
	
	@RequestMapping("/setmanual/{device}/{zone}/{on}")
	public ModelAndView manualSetOn(@PathVariable long device, @PathVariable int zone, @PathVariable int on) {
//		HeatingSettings heatingSettings = repo.getSettings(device); // This may throw an excepiton -> e.g. invalid device
		//HeatingSettings heatingSettings = repo.findOne(device);
		HeatingSettings heatingSettings = repo.getOne(device);
		
		if (heatingSettings.getZones().size() < zone)
			throw new IllegalArgumentException("Invalid zone;"); // TODO: Create ean exception
		
		if (on != 0 && on != 1)
			throw new IllegalArgumentException("Invalid on/off status;"); // TODO: Create ean exception
		
//		heatingSettings.getZones().get(zone).setManualModeSetting( (on == 1) );
		heatingSettings.getZones().get(zone).setMode(ZoneMode.MANUAL_ON);
		System.out.println("Saving:" + heatingSettings);
//		repo.setSettings(device, heatingSettings);
		repo.save(heatingSettings); // FIXME: This does not guarantee device number.		
		
		ModelAndView modelAndView = new ModelAndView("main");
		modelAndView.addObject("heatingSettings", heatingSettings);
		return modelAndView;
	}
	
	
	
}
