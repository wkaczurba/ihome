package com.ihome.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class HeatingSettings implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 253595098289219101L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	@Column(name="HEATING_SETTINGS_ID")
	private Long id;
	
	// TODO: Add the following deviceId that is unique.
	@Column(unique=true)
	private long deviceId; // device Id;	
	
	/**
	 * @return the deviceId
	 */
	public long getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	@OneToMany(cascade = CascadeType.ALL /*, fetch=FetchType.EAGER*/, orphanRemoval=true)
	@JoinColumn(name="HEATING_SETTINGS_ID")
	private List<ZoneSetting> zones = new ArrayList<>();
	
	//@SuppressWarnings("unused")
	private HeatingSettings() { 
	}
	
	public HeatingSettings(long deviceId, ZoneSetting... zones) {
		this.deviceId = deviceId;
		for (ZoneSetting zone : zones) {
			this.zones.add(zone);
		}
	}

	// FIXME: Fix and test this function.
	public HeatingSettings(long deviceId, List<ZoneSetting> zones) {
		this.deviceId = deviceId;
		throw new IllegalStateException("This funciton should not be called");
	}

	// COPYING-CONSTRUCTOR
	public HeatingSettings(long deviceId, HeatingSettings h) {
		this.deviceId = deviceId;
		h.getZones().stream().forEach(z -> zones.add(new ZoneSetting(z)) );
	}
	
	public static HeatingSettings createRandom(long deviceId) {
		HeatingSettings hs = new HeatingSettings();
		hs.setDeviceId(deviceId);
		IntStream.range(0, (new Random()).nextInt(10) ).forEach(i -> hs.getZones().add( ZoneSetting.createRandom() ));
		
		return hs;
	}

	/**
	 * @return the zones
	 */
	public List<ZoneSetting> getZones() {
		return zones;
	}

	public void addZone(ZoneSetting zone) {
		zones.add(zone);
	}
	
	public void addAllZones(Collection<? extends ZoneSetting> zones) {
		this.zones.addAll(zones);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HeatingSettings id=\""+ id +"\"; deviceId="+ deviceId +":\n"); 
		if (zones == null) {
			sb.append("zones == null\n");
		} else {
			for (int i=0; i<zones.size(); i++) {
				sb.append((i + ": " + zones.get(i).toString()) .replaceAll("(?m)^", "    "));
				if (i < zones.size() - 1)
					sb.append("\n");
			}
			sb.deleteCharAt(sb.lastIndexOf("\n"));
		}
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
		result = prime * result + (int) (deviceId ^ (deviceId >>> 32));
		result = prime * result + ((zones == null) ? 0 : zones.hashCode());
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
		HeatingSettings other = (HeatingSettings) obj;
		if (deviceId != other.deviceId)
			return false;
		if (zones == null) {
			if (other.zones != null)
				return false;
		} else if (!zones.equals(other.zones))
			return false;
		return true;
	}

//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		return HashCodeBuilder.reflectionHashCode(this, "id");
//	}
//
//	/* (non-Javadoc)
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object that) {
//		return EqualsBuilder.reflectionEquals(this, that, "id");
//	}
	
	
}
