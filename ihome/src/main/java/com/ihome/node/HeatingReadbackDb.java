package com.ihome.node;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HeatingReadbackDb implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1061914710785471647L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)		
	private Long id; // database Id;
	
	@Column(unique=true)
	private long deviceId; // device Id;
	private long heatingOn;
	// As is, no need for ISO; 
	private OffsetDateTime timeStamp;
		
	@SuppressWarnings("unused")
	private HeatingReadbackDb() {}
	
	public HeatingReadbackDb(long deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

// This is dealt with by Hibernate.
//	/**
//	 * @param id the id to set
//	 */
//	public void setId(Long id) {
//		this.id = id;
//	}

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

	/**
	 * @return the heatingOn
	 */
	public long getHeatingOn() {
		return heatingOn;
	}

	/**
	 * @param heatingOn the heatingOn to set
	 */
	public void setHeatingOn(long heatingOn) {
		this.heatingOn = heatingOn;
	}
	
	/**
	 * Convenience method
	 * @param rb
	 */
	public void setHeatingOn(HeatingReadbackRest rb) {
		List<Boolean> rbHeatingOn = rb.getHeatingOn();
		this.heatingOn = 0L;
		
		for (int i=0; i<rbHeatingOn.size(); i++) {
			if ( rbHeatingOn.get(i) ) // if i-th element is true...
				this.heatingOn |=  1 << i;   // set appropriate bit to 1.
		}
	}

	/**
	 * @return the timeStamp
	 */
	public OffsetDateTime getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(OffsetDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (deviceId ^ (deviceId >>> 32));
		result = prime * result + (int) (heatingOn ^ (heatingOn >>> 32));
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
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
		HeatingReadbackDb other = (HeatingReadbackDb) obj;
		if (deviceId != other.deviceId)
			return false;
		if (heatingOn != other.heatingOn)
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HeatingReadbackDb [id=" + id + ", deviceId=" + deviceId + ", heatingOn=" + heatingOn + ", timeStamp="
				+ timeStamp + "]";
	}

}
