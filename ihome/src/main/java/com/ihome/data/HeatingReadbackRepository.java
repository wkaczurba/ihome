package com.ihome.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihome.node.HeatingReadbackDb;
import com.ihome.node.HeatingSettings;

public interface HeatingReadbackRepository extends JpaRepository<HeatingReadbackDb, Long> {
	
	//List<HeatingReadbackDb> findByDeviceId(long deviceId);
	
	// TODO: Add along with JPA.
	//List<HeatingReadbackDb> findByDeviceId(long deviceId);
	HeatingReadbackDb findOneByDeviceId(long deviceId);
	HeatingReadbackDb getOneByDeviceId(long deviceId);	
}