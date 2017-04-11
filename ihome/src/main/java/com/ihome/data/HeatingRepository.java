package com.ihome.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihome.node.HeatingReadbackDb;
import com.ihome.node.HeatingSettings;
import com.ihome.node.ZoneMode;
import com.ihome.node.ZoneSetting;
import com.ihome.node.ZoneTimerEntry;

public interface HeatingRepository extends JpaRepository<HeatingSettings, Long> {
	
	/**
	 * Creates initial heating 
	 * @return
	 */
	default HeatingSettings createInitial() {
		int deviceId = 0;
		
		HeatingSettings hs = this.getOneByDeviceId(deviceId);
		
		if (hs == null) {
			hs = new HeatingSettings(deviceId, 
				new ZoneSetting(ZoneMode.MANUAL_ON, ZoneTimerEntry.create("7:00", "5:00", "monday, tuesday", "january,february")),
				new ZoneSetting(ZoneMode.MANUAL_ON, ZoneTimerEntry.create("17:00", "23:33", "monday, tuesday", "january,february")),
				new ZoneSetting(ZoneMode.MANUAL_ON, ZoneTimerEntry.create("7:00", "5:00", "monday, tuesday", "january,february")));
		}
		
		return this.save(hs);				
	}
	
	// TODO: Add along with JPA.
	List<HeatingSettings> findByDeviceId(long deviceId);
	HeatingSettings findOneByDeviceId(long deviceId);
	HeatingSettings getOneByDeviceId(long deviceId);
}
