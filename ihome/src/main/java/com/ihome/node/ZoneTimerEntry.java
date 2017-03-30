package com.ihome.node;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ZoneTimerEntry implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9026775956199581651L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	@Column(name="ZONE_TIMER_ENTRY_id")
	private Long id;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime startingTime;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime endTime;
	
	// TODO: Follow this: http://stackoverflow.com/questions/2233943/persisting-a-set-of-days-of-the-week
	// Try also this one: http://www.concretepage.com/hibernate/elementcollection_hibernate_annotation
	// And: http://stackoverflow.com/questions/416208/jpa-map-collection-of-enums
	
	@ElementCollection(targetClass=DayOfWeek.class, fetch=FetchType.EAGER)
	@JoinTable(name="tblDayOfWeek", joinColumns = @JoinColumn(name="zoneTimerEntry_id"))
	@Column(name="days", nullable=false)
	@Enumerated(EnumType.STRING)
	private Set<DayOfWeek> days = new HashSet<>();
	
	@ElementCollection(targetClass=Month.class, fetch=FetchType.EAGER)
	@JoinTable(name="tblMonth", joinColumns = @JoinColumn(name="zoneTimerEntry_id"))
	@Column(name="months", nullable=false)
	@Enumerated(EnumType.STRING)
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
	
	// Convenience method.
	/**
	 * 
	 * @param startHour - e.g. "17:03"
	 * @param endHour - e.g. "22:03"
	 * @param days - "Monday, Tuesday, Friday"
	 * @param months - "JANUARY, February, March"
	 */
	public static ZoneTimerEntry create(String startHour, String endHour, String days, String months) {
		String[] start = startHour.split(":");
		String[] end = endHour.split(":");
		Set<Month> m = Arrays.<String>asList( months.split(",") )
				.stream()
				.map(s -> s.trim().toUpperCase() )
				.map(x -> Month.valueOf(x))
				.collect(Collectors.toSet());
		Set<DayOfWeek> d = Arrays.<String>asList( days.split(",") )
				.stream()
				.map(s -> s.trim().toUpperCase() )
				.map(x -> DayOfWeek.valueOf(x))
				.collect(Collectors.toSet());

		LocalTime s = LocalTime.of(Integer.parseInt(start[0]), Integer.parseInt(start[1])); 
		LocalTime e = LocalTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
		
		return new ZoneTimerEntry(s, e, d, m);
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
//		return "ZoneTimerEntry [id=" + id + ", startingTime=" + startingTime + ", endTime=" + endTime + ", days=" + days
//				+ ", months=" + months + "]";
		return "ZoneTimerEntry [id=" + id 
				+ ", time: " + startingTime 
				+ "-" + endTime + (days.size() > 0 ? " on: " : "") 
				+ days.stream().map(s -> s.toString()).collect(Collectors.joining(","))
		        + (months.size() > 0 ? " months: " : "")
				+ months.stream().map(s -> s.toString()).collect(Collectors.joining(","))
				+ "]";
		//+ ", months=" + months + "]";
		
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id");
	}

	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that, "id");
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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