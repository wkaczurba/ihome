package com.ihome;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ihome.data.HeatingReadbackRepository;
import com.ihome.data.HeatingRepository;
import com.ihome.node.CurrentStatus;
import com.ihome.node.HeatingSettings;
import com.ihome.node.HeatingReadbackDb;
import com.ihome.node.HeatingReadbackRest;
import com.ihome.node.ZoneMode;
import com.ihome.node.ZoneSetting;
import com.ihome.node.ZoneTimerEntry;

@SuppressWarnings("deprecation")
@RestController
public class DataController {
//	@RequestMapping("/current")
//	public CurrentStatus current() {
//		return new CurrentStatus();
//	}
	
	Logger logger = LoggerFactory.getLogger(DataController.class);
	HeatingRepository repo;
	HeatingReadbackRepository readbackRepo;
	
	@Autowired
	public DataController(HeatingRepository repo, HeatingReadbackRepository readbackRepo) {
		this.repo = repo;
		this.readbackRepo = readbackRepo;
	}

	// FIXME: Remove or fix; this one fails.
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
	
	// TODO: Move to separate "test-controller" if needed or remove this one
	@RequestMapping("/testget")
	public ZoneTimerEntry testgettime() {
		ZoneTimerEntry z = new ZoneTimerEntry(
				LocalTime.of(20, 34), 
				LocalTime.of(8, 34),
				new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.THURSDAY)),
				new HashSet<Month>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)));
		return z;	
	}
	
	// TODO: Move to separate "test-controller" if needed or remove this one	
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

		// FIXME: The constructor below is liekely to throw an exception
		HeatingSettings heating = new HeatingSettings(0, Arrays.asList(z1, z2, z3));
		return heating;	
	}	
	
	// TODO: Move to separate "test-controller" if needed or remove this one
	@RequestMapping("/testreadback")
	public HeatingReadbackRest testgetZonesReadback() {
		HeatingReadbackRest h = new HeatingReadbackRest(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
		return h;
	}
	
	// TODO: Move to separate "test-controller" if needed or remove this one
	@RequestMapping(path="/currentput", method=RequestMethod.PUT)
	public String putCurrentStatus(@RequestBody CurrentStatus cs) {
		System.out.println("GOT Status:" + cs);
		return "index";
	}
	
	@RequestMapping(path="/settings/{device}", method=RequestMethod.GET)
	public HeatingSettings getSettings(@PathVariable long device) {
		if (device != 0)
			throw new IllegalArgumentException("Unknown device");
		
		//return repo.getOne(device);
		//return repo.getSettings(device);
		//return repo.findOne(device);
		return repo.findOneByDeviceId(device);
	}

	// TODO: Things: Add gpio for each + timestamp when msg was received (interval every 10 seconds?)
	/**
	 * Takes HeatingReadbackRest as an input and coverts it to DB-format (HeatingReadbackDb). 
	 * 
	 * If entry does not exist in the db - it creates a new one.
	 * 
	 * @param device
	 * @param rb
	 * @return
	 */
	@RequestMapping(path="/status/{device}", method=RequestMethod.PUT)
	public String putStatus(@PathVariable long device, @RequestBody HeatingReadbackRest restRb) {
		if (device != 0)
			throw new IllegalArgumentException("Unknown device");
		
		System.out.println("GOT HeadingReadback:" + restRb);

		HeatingReadbackDb dbRb;
		dbRb = readbackRepo.getOneByDeviceId(device);
		if (dbRb == null) {
			dbRb = new HeatingReadbackDb(device);
		}
		dbRb.setHeatingOn(restRb);
		dbRb.setTimeStamp(OffsetDateTime.now());
		readbackRepo.save(dbRb);
			
		return "index";
	}
	
	/* Reads back latest status */
	@RequestMapping(path="/status/{device}", method=RequestMethod.GET)
	public HeatingReadbackDb getStatus(@PathVariable long device) {
		HeatingReadbackDb dbRb;
		
		dbRb = readbackRepo.getOneByDeviceId(device);
		return dbRb;
	}
	
//HERE:
	@RequestMapping(path="/settingsFeedback/{device}", method=RequestMethod.PUT)
	public String putFeedbackSettings(@PathVariable int device, @RequestBody HeatingSettings fbSettings) {
		if (device != 0)
			throw new IllegalArgumentException("Unknown device");
	
		// TODO: Save to repo/handle it:
		// TODO: Consider checking if the feedback is correct.
		System.out.println("fbSettings: " + fbSettings);

		return "index";
	}
	
	// TODO: Remove this one; only for tests;
	@RequestMapping(path="/test/offset", method=RequestMethod.GET)
	public OffsetDateTime offsetDateTime() {
//		ZonedDateTime zdt = ZonedDateTime.from(OffsetDateTime.now());
//		//zdt.toOffsetDateTime()
		
		return ZonedDateTime.now().toOffsetDateTime();
	}
	

	// TODO: Move to separate "test-controller" if needed or remove this one
	/**
	 *  Generates random stuff for testing of Pi-Java connection.
	 */
	HeatingSettings heatingSettingsLoopbackTest;
	@RequestMapping(path="/test/heatingSeetingloopback", method=RequestMethod.GET)
	public HeatingSettings heatingSettingsLoopbackTestGet() {
		heatingSettingsLoopbackTest = HeatingSettings.createRandom(0);
		HeatingSettings h2 = new HeatingSettings(0, heatingSettingsLoopbackTest);
		
		if (!h2.equals(heatingSettingsLoopbackTest)) {
			System.out.println("heatingSettingsLoopbackTest = " + heatingSettingsLoopbackTest);
			System.out.println("h2 = " + h2);			
			throw new RuntimeException("Equality failed");
		}
		
		logger.info("sending: heatingSettingsLoopbackTest = " + heatingSettingsLoopbackTest.toString());
		return heatingSettingsLoopbackTest;
	}

	// TODO: Move to separate "test-controller" if needed or remove this one	
	/**
	 *  Receive random stuff and check it matches.
	 * @param fbSetting - feedback.
	 * @return
	 */
	@RequestMapping(path="/test/heatingSeetingloopback", method=RequestMethod.PUT)
	public String heatingSettingsLoopbackTestPut(@RequestBody HeatingSettings fbSetting) {
		if (!fbSetting.equals(heatingSettingsLoopbackTest)) {
			System.out.println("heatingSettingsLoopbackTest = " + heatingSettingsLoopbackTest);
			System.out.println("fbSettings = " + fbSetting);
			
			throw new RuntimeException("Mismatch between what was supplied and returned");
			
		}
		return "index";
	}

	
}
