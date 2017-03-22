package com.ihome.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihome.node.HeatingSettings;

public interface HeatingRepository extends JpaRepository<HeatingSettings, Long> {
//	public HeatingSettings getSettings(int device);
//	public void setSettings(int device, HeatingSettings settings);
}
