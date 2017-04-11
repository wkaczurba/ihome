package com.ihome.node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeatingReadbackRest {
	private List<Boolean> heatingOn;
	
	@SuppressWarnings("unused")
	private HeatingReadbackRest() {}
	
	public HeatingReadbackRest(Boolean...heating) {
		heatingOn = new ArrayList<>(Arrays.asList(heating));
	}

	/**
	 * @return the heatingOn
	 */
	public List<Boolean> getHeatingOn() {
		return heatingOn;
	}

	/**
	 * @param heatingOn the heatingOn to set
	 */
	public void setHeatingOn(List<Boolean> heatingOn) {
		this.heatingOn = heatingOn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((heatingOn == null) ? 0 : heatingOn.hashCode());
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
		HeatingReadbackRest other = (HeatingReadbackRest) obj;
		if (heatingOn == null) {
			if (other.heatingOn != null)
				return false;
		} else if (!heatingOn.equals(other.heatingOn))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HeatingReadback [heatingOn=" + heatingOn + "]";
	};
	
	
}
