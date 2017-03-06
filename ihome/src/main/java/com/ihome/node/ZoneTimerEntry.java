package com.ihome.node;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ZoneTimerEntry implements Serializable {
	private LocalTime startingTime;
	private LocalTime endTime;
	private Set<DayOfWeek> days = new HashSet<>();
	private Set<Month> months = new HashSet<>();

	private ZoneTimerEntry() {}
	

	public static ZoneTimerEntry createRandom() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		
		LocalTime startingTime = LocalTime.of(rand.nextInt(24), rand.nextInt(60));
		LocalTime endTime = LocalTime.of(rand.nextInt(24), rand.nextInt(60));
		
		Set<DayOfWeek> days = new HashSet<>();
		Arrays.asList(DayOfWeek.values()).stream().filter(x -> (Math.random() < 1./7)).forEach(d -> days.add(d));
		
		Set<Month> months = new HashSet<>();
		Arrays.asList(Month.values()).stream().filter(x->(Math.random() < 1./12)).forEach(m -> months.add(m));
		
		return new ZoneTimerEntry(startingTime, endTime, days, months);
	}	

	public ZoneTimerEntry(LocalTime startingTime, LocalTime endTime, Set<DayOfWeek> days, Set<Month> months) {
		super();
		this.startingTime = startingTime;
		this.endTime = endTime;
		this.days = days;
		this.months = months;
	}
	
	// TODO: Remove copying constructor
	public ZoneTimerEntry(ZoneTimerEntry z) {
		this(z.startingTime, z.endTime, new HashSet<>(z.days), new HashSet<>(z.months));
	}

	/**
	 * @return the startingTime
	 */
	public LocalTime getStartingTime() {
		return startingTime;
	}

	/**
	 * @param startingTime the startingTime to set
	 */
	public void setStartingTime(LocalTime startingTime) {
		this.startingTime = startingTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the days
	 */
	public Set<DayOfWeek> getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(Set<DayOfWeek> days) {
		this.days = days;
	}

	/**
	 * @return the months
	 */
	public Set<Month> getMonths() {
		return months;
	}

	/**
	 * @param months the months to set
	 */
	public void setMonths(Set<Month> months) {
		this.months = months;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ZoneTimerEntry [startingTime=" + startingTime + ", endTime=" + endTime + ", days=" + days + ", months="
				+ months + "]";
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that);
	}
	

//	public static void main(String[] args) {
//		ZoneTimerEntry z = new ZoneTimerEntry(
//				LocalTime.of(20, 0),
//				LocalTime.of(20, 34),
//				new HashSet<DayOfWeek>(Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.THURSDAY)),
//				new HashSet<Month>(Arrays.asList(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)));
//		
//		System.out.println("Example:" + z);
//		
//	}
	
	
}