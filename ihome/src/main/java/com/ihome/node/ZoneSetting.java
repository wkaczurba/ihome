package com.ihome.node;

import java.util.HashSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class ZoneSetting implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 985658386797337594L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ZONE_SETTING_ID")
	private Long id;
	private ZoneMode mode;
	//private Boolean manualModeSetting;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	//@JoinColumn(name = "STOCK_ID", nullable = false)	
//	HeatingSettings heatingSettings;  
	
	@OneToMany(cascade = CascadeType.ALL, /*fetch=FetchType.EAGER*/ orphanRemoval=true)
	@JoinColumn(name="ZONE_SETTING_ID")
	private Set<ZoneTimerEntry> automaticModeSettings = new HashSet<>();
	
	private ZoneSetting() {}
	
	// Randomize stuff:
	public static ZoneSetting createRandom() {
		ZoneMode mode = ZoneMode.values()[(int) (Math.random() * ZoneMode.values().length)];
//		Boolean manualModeSetting = (Math.random() > 0.5);
		
		Set<ZoneTimerEntry> automaticModeSettings = new HashSet<>();
		IntStream.range(0, (new Random()).nextInt(10)).forEach(x -> automaticModeSettings.add(ZoneTimerEntry.createRandom()));
		
		return new ZoneSetting(mode, /*manualModeSetting,*/ automaticModeSettings);
		
	}
	
	public ZoneSetting(ZoneMode mode, /*Boolean manualModeSetting,*/ ZoneTimerEntry... automaticModeSettings) {
		this.mode = mode;
//		this.manualModeSetting = manualModeSetting;		
		for (ZoneTimerEntry zte : automaticModeSettings) {
			this.automaticModeSettings.add(zte);
		}
	}
	
	public ZoneSetting(ZoneMode mode, /*Boolean manualModeSetting,*/ Set<ZoneTimerEntry> automaticModeSettings) {
		this(mode, /*manualModeSetting,*/ automaticModeSettings.toArray(new ZoneTimerEntry[0]));
//		this.mode = mode;
//		this.manualModeSetting = manualModeSetting;
//		this.automaticModeSettings.addAll(automaticModeSettings);
	}	

	/*
	public HeatingSettings getHeatingSettings() {
		return heatingSettings;
	}	
	public void setHeatingSettings(HeatingSettings hs) {
		this.heatingSettings = hs;
	} */
		
//	// TODO: Remove copying constructor;
//	public ZoneSetting(ZoneSetting zoneSetting) {
//		mode = zoneSetting.mode;
//		manualModeSetting = zoneSetting.manualModeSetting;
//		
//		zoneSetting.automaticModeSettings.stream().forEach(x -> automaticModeSettings.add(new ZoneTimerEntry(x)));
//	}

		
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
//	/**
//	 * @return the manualModeSetting
//	 */
//	public Boolean getManualModeSetting() {
//		return manualModeSetting;
//	}
//	/**
//	 * @param manualModeSetting the manualModeSetting to set
//	 */
//	public void setManualModeSetting(Boolean manualModeSetting) {
//		this.manualModeSetting = manualModeSetting;
//	}
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
//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		return HashCodeBuilder.reflectionHashCode(this, "id");
//	}
//	/* (non-Javadoc)
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object that) {
//		return EqualsBuilder.reflectionEquals(this, that, "id");
//	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
//		return "ZoneSetting [id=" + id + ", mode=" + mode + ", manualModeSetting=" + manualModeSetting
//				+ ", automaticModeSettings=" + automaticModeSettings + "]";
		StringBuilder sb = new StringBuilder();
		
		sb.append("ZoneSetting id=" + id + ", mode=" + mode + /*", manualModeSetting=" + manualModeSetting + */ ":\n");
		automaticModeSettings.forEach(x -> {
			sb.append(x.toString().replaceAll("(?m)^", "\t"));
			sb.append("\n");
		});
		sb.deleteCharAt(sb.lastIndexOf("\n"));
		
		return sb.toString();
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((automaticModeSettings == null) ? 0 : automaticModeSettings.hashCode());
//		result = prime * result + ((manualModeSetting == null) ? 0 : manualModeSetting.hashCode());
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZoneSetting other = (ZoneSetting) obj;
		if (automaticModeSettings == null) {
			if (other.automaticModeSettings != null)
				return false;
		} else if (!automaticModeSettings.equals(other.automaticModeSettings))
			return false;
//		if (manualModeSetting == null) {
//			if (other.manualModeSetting != null)
//				return false;
//		} else if (!manualModeSetting.equals(other.manualModeSetting))
//			return false;
		if (mode != other.mode)
			return false;
		return true;
	}

	
}
