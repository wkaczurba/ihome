package com.ihome.node;

import java.util.HashSet;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ZoneSetting implements Serializable {
	private long id;
	private ZoneMode mode;
	private Boolean manualModeSetting;
	private Set<ZoneTimerEntry> automaticModeSettings = new HashSet<>();
	
	private ZoneSetting() {}
	
	// Randomize stuff:
	public static ZoneSetting createRandom() {
		ZoneMode mode = ZoneMode.values()[(int) (Math.random() * ZoneMode.values().length)];
		Boolean manualModeSetting = (Math.random() > 0.5);
		
		Set<ZoneTimerEntry> automaticModeSettings = new HashSet<>();
		IntStream.range(0, (new Random()).nextInt(10)).forEach(x -> automaticModeSettings.add(ZoneTimerEntry.createRandom()));
		
		return new ZoneSetting(mode, manualModeSetting, automaticModeSettings);
		
	}
	
	public ZoneSetting(ZoneMode mode, Boolean manualModeSetting, Set<ZoneTimerEntry> automaticModeSettings) {
		super();
		this.mode = mode;
		this.manualModeSetting = manualModeSetting;
		this.automaticModeSettings = automaticModeSettings;
	}
	
	public ZoneSetting(ZoneMode mode, Boolean manualModeSetting, ZoneTimerEntry... automaticModeSettings) {
		this(mode, manualModeSetting, new HashSet<>(Arrays.asList(automaticModeSettings)));
	}	
	
	// TODO: Remove copying constructor;
	public ZoneSetting(ZoneSetting zoneSetting) {
		mode = zoneSetting.mode;
		manualModeSetting = zoneSetting.manualModeSetting;
		
		zoneSetting.automaticModeSettings.stream().forEach(x -> automaticModeSettings.add(new ZoneTimerEntry(x)));
	}

		
	/**
	 * @return the mode
	 */
	public ZoneMode getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(ZoneMode mode) {
		this.mode = mode;
	}
	/**
	 * @return the manualModeSetting
	 */
	public Boolean getManualModeSetting() {
		return manualModeSetting;
	}
	/**
	 * @param manualModeSetting the manualModeSetting to set
	 */
	public void setManualModeSetting(Boolean manualModeSetting) {
		this.manualModeSetting = manualModeSetting;
	}
	/**
	 * @return the automaticModeSettings
	 */
	public Set<ZoneTimerEntry> getAutomaticModeSettings() {
		return automaticModeSettings;
	}
	/**
	 * @param automaticModeSettings the automaticModeSettings to set
	 */
	public void setAutomaticModeSettings(Set<ZoneTimerEntry> automaticModeSettings) {
		this.automaticModeSettings = automaticModeSettings;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Zone [mode=" + mode + ", manualModeSetting=" + manualModeSetting + ", automaticModeSettings="
				+ automaticModeSettings + "]";
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

//	/**
//	 * @param id the id to set
//	 */
//	public void setId(long id) {
//		this.id = id;
//	}

	
}
