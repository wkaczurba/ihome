package com.ihome.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
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
public class HeatingSettings implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 253595098289219101L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	@Column(name="HEATING_SETTINGS_ID")
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL /*, fetch=FetchType.EAGER*/, orphanRemoval=true)
	@JoinColumn(name="HEATING_SETTINGS_ID")
	private List<ZoneSetting> zones = new ArrayList<>();
	
	//@SuppressWarnings("unused")
	private HeatingSettings() { 
	}
	
	public HeatingSettings(ZoneSetting... zones) {
		for (ZoneSetting zone : zones) {
			this.zones.add(zone);
		}
	}
	
	public HeatingSettings(List<ZoneSetting> zones) {
		throw new IllegalStateException("This funciton should not be called");
//		this.zones.clear();
//		this.zones.addAll(zones);
		//this.zones = zones;
	}
	
//	public HeatingSettings(HeatingSettings h) {
//		h.getZones().stream().forEach(z -> zones.add(new ZoneSetting(z)) );
//	}
	
	public static HeatingSettings createRandom() {
		// TODO Auto-generated method stub
		List<ZoneSetting> zones = new ArrayList<>();
		
		HeatingSettings hs = new HeatingSettings();
		
		IntStream.range(0, (new Random()).nextInt(10) ).forEach(i -> hs.getZones().add( ZoneSetting.createRandom() ));
		
		return hs;
	}

	/**
	 * @return the zones
	 */
	public List<ZoneSetting> getZones() {
		return zones;
	}

//	/**
//	 * @param zones the zones to set
//	 */
//	public void setZones(List<ZoneSetting> zones) {
//		//this.zones.clear();
//		//this.zones.addAll(zones);
//		//this.zones = new ArrayList<>(zones);
//		//this.zones = zones;
//		this.zones = zones;
//	}
	
	public void addZone(ZoneSetting zone) {
		zones.add(zone);
	}
	
	public void addAllZones(Collection<? extends ZoneSetting> zones) {
		this.zones.addAll(zones);
	}

//	@Override
//	public String toString() {
//		return "HeatingSettings [id=" + id + ", zones=" + zones + "]";
//	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HeatingSettings id=\""+ id +"\":\n"); 
		if (zones == null) {
			sb.append("zones == null\n");
		} else {
			for (int i=0; i<zones.size(); i++) {
				sb.append((i + ": " + zones.get(i).toString()) .replaceAll("(?m)^", "    "));
				if (i < zones.size() - 1)
					sb.append("\n");
			}
			sb.deleteCharAt(sb.lastIndexOf("\n"));
			
//			zones.forEach(x -> {
//				sb.append(x.toString().replaceAll("(?m)^", "\t"));
//				sb.append("\n");
//			});
			
			
//			sb.append(
//				zones.stream().map(z -> {
//					return Arrays.<String>asList(z.toString().split("\n"))
//						.stream()
//						.map(s -> "  " + s.replace("\r", "").trim())
//						.collect(Collectors.joining(String.format("%n")));
//				}).map(s -> "  " + s)
//					.collect(Collectors.joining(String.format("%n")))
//			);
			
			
		}
		//sb.append("]");
		
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

//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((zones == null) ? 0 : zones.hashCode());
//		return result;
//	}
//
//	/* (non-Javadoc)
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		HeatingSettings other = (HeatingSettings) obj;
//		if (zones == null) {
//			if (other.zones != null)
//				return false;
//		} else { /*if (!zones.equals(other.zones))
//			return false;*/
//
//		
//			// This is hack as List will be replaced with PersistenceBag.
//			// The latter does not compare elements one after another but compares object references only.  
//			if (zones.size() != other.zones.size())
//				return false;
//			for (int i=0; i < zones.size(); i++)
//				if (!zones.get(i).equals(other.zones.get(i)))
//					return false;
//					
//		}
//		return true;
//	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that, "id");
	}
}
