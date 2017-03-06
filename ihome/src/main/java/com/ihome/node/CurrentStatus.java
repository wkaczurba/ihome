package com.ihome.node;

@Deprecated
public class CurrentStatus {
	private String name;
	private Boolean connected;
	private Boolean heatingOn;
	private int serial;
	private double temp;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the connected
	 */
	public Boolean getConnected() {
		return connected;
	}

	/**
	 * @param connected the connected to set
	 */
	public void setConnected(Boolean connected) {
		this.connected = connected;
	}

	/**
	 * @return the heatingOn
	 */
	public Boolean getHeatingOn() {
		return heatingOn;
	}

	/**
	 * @param heatingOn the heatingOn to set
	 */
	public void setHeatingOn(Boolean heatingOn) {
		this.heatingOn = heatingOn;
	}

	/**
	 * @return the serial
	 */
	public int getSerial() {
		return serial;
	}

	/**
	 * @param serial the serial to set
	 */
	public void setSerial(int serial) {
		this.serial = serial;
	}

	/**
	 * @return the temp
	 */
	public double getTemp() {
		return temp;
	}

	/**
	 * @param temp the temp to set
	 */
	public void setTemp(double temp) {
		this.temp = temp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CurrentStatus [name=" + name + ", connected=" + connected + ", heatingOn=" + heatingOn + ", serial="
				+ serial + ", temp=" + temp + "]";
	}
	
	
	
}
