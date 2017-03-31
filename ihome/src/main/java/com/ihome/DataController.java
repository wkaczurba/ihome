package com.ihome;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ihome.data.HeatingRepository;
import com.ihome.node.CurrentStatus;
import com.ihome.node.HeatingSettings;
import com.ihome.node.HeatingReadback;
import com.ihome.node.ZoneMode;
import com.ihome.node.ZoneSetting;
import com.ihome.node.ZoneTimerEntry;

@RestController
public class DataController {
//	@RequestMapping("/current")
//	public CurrentStatus current() {
//		return new CurrentStatus();
//	}
	
	Logger logger = LoggerFactory.getLogger(DataController.class);
	HeatingRepository repo;
	
	@Autowired
	public DataController(HeatingRepository repo) {
		this.repo = repo;
	}

	@RequestMapping("/current")
	public CurrentStatus currentId(
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="connected", required=false) Boolean connected,
			@RequestParam(value="heatingOn", required=false) Boolean heatingOn,
			@RequestParam(value="serial", required=false) Integer serial,
			@RequestParam(value="temp", required=false) Double temp) {
		CurrentStatus c = new CurrentStatus();
		c.setName(name);
		c.setConnected(connected);
		c.setHeatingOn(heatingOn);
		c.setSerial(serial);
		c.setTemp(temp);
		return c;
	}
	
	// TODO: Remove this one (it is only temporary)
	@RequestMapping("/testget")
	public ZoneTimerEntry testgettime() {
		ZoneTimerEntry z = new ZoneTimerEntry(
				LocalTime.of(20, 34), 
				LocalTime.of(8, 34),
				new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.THURSDAY)),
				new HashSet<Month>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)));
		return z;	
	}
	
	// TODO: Reomove, Temp only	
	@RequestMapping("/testget2")
	public HeatingSettings testgetZones() {
		ZoneTimerEntry z1a = new ZoneTimerEntry(
				LocalTime.of(20, 34), 
				LocalTime.of(8, 34),
				new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.THURSDAY)),
				new HashSet<Month>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)));
		ZoneTimerEntry z1b = new ZoneTimerEntry(
				LocalTime.of(20, 34), 
				LocalTime.of(8, 34),
				new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)),
				new HashSet<Month>(Arrays.asList(Month.NOVEMBER, Month.OCTOBER, Month.MARCH)));
		
		ZoneTimerEntry z2a = new ZoneTimerEntry(
				LocalTime.of(15, 34), 
				LocalTime.of(20, 0),
				new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.THURSDAY)),
				new HashSet<Month>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)));
		ZoneTimerEntry z2b = new ZoneTimerEntry(
				LocalTime.of(21, 10), 
				LocalTime.of(20, 10),
				new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)),
				new HashSet<Month>(Arrays.asList(Month.NOVEMBER, Month.OCTOBER, Month.MARCH)));

		ZoneSetting z1 = new ZoneSetting(ZoneMode.AUTOMATIC/*, false,*/, z1a, z1b);
		ZoneSetting z2 = new ZoneSetting(ZoneMode.AUTOMATIC/*, false,*/, z2a, z2b);
		ZoneSetting z3 = new ZoneSetting(ZoneMode.MANUAL_ON/*, true*/);

		HeatingSettings heating = new HeatingSettings(Arrays.asList(z1, z2, z3));
		return heating;	
	}	
	
	// TODO: Reomove, Temp only
	@RequestMapping("/testreadback")
	public HeatingReadback testgetZonesReadback() {
		HeatingReadback h = new HeatingReadback(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
		return h;
	}
	
	// TODO: Reomove, Temp only	
	@RequestMapping(path="/currentput", method=RequestMethod.PUT)
	public String putCurrentStatus(@RequestBody CurrentStatus cs) {
		System.out.println("GOT Status:" + cs);
		return "index";
	}
	
	
//	// TODO: Change so it uses /status/{device}
//	@RequestMapping(path="/status/0", method=RequestMethod.PUT)
//	public String putStatus(@RequestBody HeatingReadback rb) {
//		System.out.println("GOT HeadingReadback:" + rb);
//		return "index";
//	}

	@RequestMapping(path="/status/{device}", method=RequestMethod.PUT)
	public String putStatus(@PathVariable int device, @RequestBody HeatingReadback rb) {
		if (device != 1)
			throw new IllegalArgumentException("Unknown device");
		
		System.out.println("GOT HeadingReadback:" + rb);
		return "index";
	}

	@RequestMapping(path="/settings/{device}", method=RequestMethod.GET)
	public HeatingSettings getSettings(@PathVariable long device) {
		if (device != 1)
			throw new IllegalArgumentException("Unknown device");
		
		//return repo.getOne(device);
		//return repo.getSettings(device);
		return repo.findOne(device);
	}
	
	@RequestMapping(path="/settingsFeedback/{device}", method=RequestMethod.PUT)
	public String putFeedbackSettings(@PathVariable int device, @RequestBody HeatingSettings fbSettings) {
		if (device != 1)
			throw new IllegalArgumentException("Unknown device");
	
		// TODO: Save to repo/handle it:
		System.out.println("fbSettings: " + fbSettings);

		return "index";
	}

/*
 TODO: To reenable the stuff below - will need to re-enable copying constructor in HeatingSettings.
 * 	
	// Generate random stuff
	HeatingSettings heatingSettingsLoopbackTest;
	@RequestMapping(path="/test/heatingSeetingloopback", method=RequestMethod.GET)
	public HeatingSettings heatingSettingsLoopbackTestGet() {
		heatingSettingsLoopbackTest = HeatingSettings.createRandom();
		HeatingSettings h2 = new HeatingSettings(heatingSettingsLoopbackTest);
		
		if (!h2.equals(heatingSettingsLoopbackTest)) {
			System.out.println("heatingSettingsLoopbackTest = " + heatingSettingsLoopbackTest);
			System.out.println("h2 = " + h2);			
			throw new RuntimeException("Equality failed");
		}
		
		logger.info("sending: heatingSettingsLoopbackTest = " + heatingSettingsLoopbackTest.toString());
		return heatingSettingsLoopbackTest;
	}

	// Receive random stuff and check it matches.
	@RequestMapping(path="/test/heatingSeetingloopback", method=RequestMethod.PUT)
	public String heatingSettingsLoopbackTestPut(@RequestBody HeatingSettings fbSetting) {
		if (!fbSetting.equals(heatingSettingsLoopbackTest)) {
			System.out.println("heatingSettingsLoopbackTest = " + heatingSettingsLoopbackTest);
			System.out.println("fbSettings = " + fbSetting);
			
			throw new RuntimeException("Mismatch between what was supplied and returned");
			
		}
		return "index";
	}
*/	
	
}
