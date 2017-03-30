package com.ihome.data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihome.node.HeatingSettings;
import com.ihome.node.ZoneMode;
import com.ihome.node.ZoneSetting;
import com.ihome.node.ZoneTimerEntry;

public interface HeatingRepository extends JpaRepository<HeatingSettings, Long> {
	
	/**
	 * Creates initial heating 
	 * @return
	 */
	default HeatingSettings createInitial() {
		HeatingSettings hs = new HeatingSettings(
				new ZoneSetting(ZoneMode.MANUAL, true, ZoneTimerEntry.create("7:00", "5:00", "monday, tuesday", "january,february")),
				new ZoneSetting(ZoneMode.MANUAL, true, ZoneTimerEntry.create("17:00", "23:33", "monday, tuesday", "january,february")),
				new ZoneSetting(ZoneMode.MANUAL, true, ZoneTimerEntry.create("7:00", "5:00", "monday, tuesday", "january,february")));			
		
/*		ZoneSetting zs0 = new ZoneSetting(ZoneMode.MANUAL, true);
		ZoneSetting zs1 = new ZoneSetting(ZoneMode.MANUAL, true);
		ZoneSetting zs2 = new ZoneSetting(ZoneMode.MANUAL, true);
		//zs0.setAutomaticModeSettings(new HashSet<>(Arrays.asList(ZoneTimerEntry.create("7:00", "5:00", "MONDAY, Tuesday", "DECEMBER, JANUARY, FEBRUARY"))));
		//zs1.setAutomaticModeSettings(new HashSet<>(Arrays.asList(new ZoneTimerEntry(LocalTime.of(7, 00), LocalTime.of(5, 0), new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)), new HashSet<>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)) ))));
		//zs2.setAutomaticModeSettings(new HashSet<>(Arrays.asList(new ZoneTimerEntry(LocalTime.of(7, 00), LocalTime.of(5, 0), new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)), new HashSet<>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)) ))));
		
		zs0.getAutomaticModeSettings().add( ZoneTimerEntry.create("1:00", "1:01", "MONDAY", "DECEMBER, JANUARY, FEBRUARY") );
		zs1.getAutomaticModeSettings().add( ZoneTimerEntry.create("2:00", "2:02", "Tuesday", "DECEMBER, JANUARY, FEBRUARY") );
		zs2.getAutomaticModeSettings().add( ZoneTimerEntry.create("3:00", "3:03", "Wednesday, Thursday", "JANUARY, FEBRUARY") );
		
		//HeatingSettings hs = new HeatingSettings(new ArrayList<>(Arrays.asList(zs0, zs1, zs2))); // TODO: Remove.

		HeatingSettings hs = new HeatingSettings();
		hs.addZone(zs0);
		hs.addZone(zs1);
		hs.addZone(zs2);*/
		
		return this.save(hs);				
	}
	
//	public HeatingSettings getSettings(int device);
//	public void setSettings(int device, HeatingSettings settings);
}
