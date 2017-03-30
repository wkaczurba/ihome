package com.ihome;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ihome.data.HeatingRepository;
import com.ihome.node.HeatingSettings;
import com.ihome.node.ZoneMode;
import com.ihome.node.ZoneSetting;
import com.ihome.node.ZoneTimerEntry;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IhomeApplicationTests {
	
	@Autowired
	HeatingRepository repo;

	@Test
	public void contextLoads() {
		
	}
	
	@Test
	@Transactional 
	public void hibernateTest() {
		// create entries first.
		
		HeatingSettings hs = new HeatingSettings(
				new ZoneSetting(ZoneMode.MANUAL, true, ZoneTimerEntry.create("7:00", "5:00", "monday, tuesday", "january,february")),
				new ZoneSetting(ZoneMode.MANUAL, true, ZoneTimerEntry.create("17:00", "23:33", "monday, tuesday", "january,february")),
				new ZoneSetting(ZoneMode.MANUAL, true, ZoneTimerEntry.create("7:00", "5:00", "monday, tuesday", "january,february")));		
		
		repo.save(hs);		
		Assert.assertEquals(3, hs.getZones().size());
		
		System.out.println(hs);
		HeatingSettings rb = repo.getOne(hs.getId());
		
		Assert.assertEquals(+3, rb.getZones().size());
		
		ZoneTimerEntry zte = ZoneTimerEntry.create("17:00", "23:00", "Friday", "December, January, February"); 
		rb.getZones().get(0).getAutomaticModeSettings().add(zte);	
		
		rb = repo.getOne(hs.getId());

		Assert.assertEquals(3, rb.getZones().size());
		System.out.println("Last readback: " + rb);
		
		zte = ZoneTimerEntry.create("12:34", "13:45", "Monday", "May"); 
		rb.getZones().get(0).getAutomaticModeSettings().add(zte);	
		
		repo.save(rb); // ?
		rb = repo.findOne(rb.getId());
		
		repo.findAll();
		
		Assert.assertEquals(3, rb.getZones().size());
		System.out.println("Final: " + rb);
	}

}
