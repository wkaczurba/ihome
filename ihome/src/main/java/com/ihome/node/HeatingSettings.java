package com.ihome.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class HeatingSettings implements Serializable {
	private long id;
	private List<ZoneSetting> zones = new ArrayList<>();
	
	private HeatingSettings() {
	}
	
	public HeatingSettings(List<ZoneSetting> zones) {
		this.zones = zones;
	}
	
	public HeatingSettings(HeatingSettings h) {
		h.getZones().stream().forEach(z -> zones.add(new ZoneSetting(z)) );
	}
	
	public static HeatingSettings createRandom() {
		// TODO Auto-generated method stub
		List<ZoneSetting> zones = new ArrayList<>();
		
		IntStream.range(0, (new Random()).nextInt(10) ).forEach(i -> zones.add( ZoneSetting.createRandom() ));
		
		
		return new HeatingSettings(zones);
	}

	/**
	 * @return the zones
	 */
	public List<ZoneSetting> getZones() {
		return zones;
	}

	/**
	 * @param zones the zones to set
	 */
	public void setZones(List<ZoneSetting> zones) {
		this.zones = zones;
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
		return "Heating [zones=" + zones + "]";
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
