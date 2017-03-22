package com.ihome.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class HeatingSettings implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 253595098289219101L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private long id;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	//private List<ZoneSetting> zones = new ArrayList<>();
	private List<ZoneSetting> zones = new ArrayList<>();
	
	private HeatingSettings() {
	}
	
	public HeatingSettings(List<ZoneSetting> zones) {
		this.zones.clear();
		this.zones.addAll(zones);
		//this.zones = zones;
	}
	
//	public HeatingSettings(HeatingSettings h) {
//		h.getZones().stream().forEach(z -> zones.add(new ZoneSetting(z)) );
//	}
	
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
		//this.zones.clear();
		//this.zones.addAll(zones);
		this.zones = new ArrayList<>(zones);
		//this.zones = zones;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HeatingSettings [id=" + id + ", zones=" + zones + "]";
	}

	/**
	 * @return the id
	 */
	public long getId() {
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
		if (zones == null) {
			if (other.zones != null)
				return false;
		} else { /*if (!zones.equals(other.zones))
			return false;*/

		
			// This is hack as List will be replaced with PersistenceBag.
			// The latter does not compare elements one after another but compares object references only.  
			if (zones.size() != other.zones.size())
				return false;
			for (int i=0; i < zones.size(); i++)
				if (!zones.get(i).equals(other.zones.get(i)))
					return false;
					
		}
		return true;
	}

	
}
