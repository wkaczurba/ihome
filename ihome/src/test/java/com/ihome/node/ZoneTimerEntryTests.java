package com.ihome.node;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;

public class ZoneTimerEntryTests {
	
	@Test
	public void createTest() {
		ZoneTimerEntry zte = ZoneTimerEntry.create("17:03", "05:23", "Monday, Friday, SUNDAY", "JanuaRY, FEBRUARY,MARCH");
		
		Assert.assertEquals(LocalTime.of(17, 03), zte.getStartingTime());
		Assert.assertEquals(LocalTime.of(5, 23), zte.getEndTime());
		Assert.assertEquals(new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY)), zte.getDays());
		Assert.assertEquals(new HashSet<Month>(Arrays.asList(Month.JANUARY, Month.FEBRUARY, Month.MARCH)), zte.getMonths());
		
		zte = ZoneTimerEntry.create("3:03", "19:23", "SUNDAY, TUESDAY, THURSDAY", "DECEMBER, May, june");
		
		Assert.assertEquals(LocalTime.of(3, 03), zte.getStartingTime());
		Assert.assertEquals(LocalTime.of(19, 23), zte.getEndTime());
		Assert.assertEquals(new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SUNDAY)), zte.getDays());
		Assert.assertEquals(new HashSet<Month>(Arrays.asList(Month.MAY, Month.JUNE, Month.DECEMBER)), zte.getMonths());
	}
}
