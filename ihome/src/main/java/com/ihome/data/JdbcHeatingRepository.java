package com.ihome.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ihome.node.HeatingSettings;
import com.ihome.node.ZoneMode;
import com.ihome.node.ZoneSetting;

@Repository
public class JdbcHeatingRepository implements HeatingRepository {
	Logger logger = LoggerFactory.getLogger(JdbcHeatingRepository.class);
	
	HeatingSettings h = new HeatingSettings(new ArrayList<ZoneSetting>()); // TODO: Remove.
	{
		h.getZones().add(new ZoneSetting(ZoneMode.MANUAL, true));
		h.getZones().add(new ZoneSetting(ZoneMode.MANUAL, true));
		h.getZones().add(new ZoneSetting(ZoneMode.MANUAL, true));
	}
	
	// TODO: Fixme.
	public HeatingSettings getSettings(int device) {
		if (device != 0)
			throw new IllegalArgumentException("heatingSettings.getSettings must be call with argument device==0");
		logger.warn("getSettings() function not implemented - returned default settings (not read from JDBC)");
		return h;
	}

	@Override
	public void setSettings(int device, HeatingSettings settings) {
		// TODO Auto-generated method stub
		if (device != 0)
			throw new IllegalArgumentException("heatingSettings.getSettings must be call with argument device==0");
		logger.warn("getSettings() function not implemented - (not read from JDBC)");
		
		logger.info("Persisiting: " + settings);
		h = new HeatingSettings(settings);
		logger.info("Persisted: " + h);
	}
}
