package com.ihome.data;

import com.ihome.node.HeatingSettings;

public interface HeatingRepository {
	public HeatingSettings getSettings(int device);
	public void setSettings(int device, HeatingSettings settings);
}
