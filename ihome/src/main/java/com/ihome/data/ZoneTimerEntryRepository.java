package com.ihome.data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ihome.node.HeatingSettings;
import com.ihome.node.ZoneMode;
import com.ihome.node.ZoneSetting;
import com.ihome.node.ZoneTimerEntry;

public interface ZoneTimerEntryRepository extends JpaRepository<ZoneTimerEntry, Long> {

}
