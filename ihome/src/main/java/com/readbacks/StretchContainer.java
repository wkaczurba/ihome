package com.readbacks;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StretchContainer<T> {
	
	private List<Stretch<T>> list = new ArrayList<>();
	private Duration maxDifference = Duration.ofSeconds(60L);
	private Stretch<T> currentStretch = null;

	public void addSample(T value, Instant instant) {
		if (currentStretch == null ||
			!currentStretch.getValue().equals(value) ||
			currentStretch.getEnd().plus(maxDifference).isBefore(instant)) {
				
			currentStretch = new Stretch<T>(value, instant);
			list.add(currentStretch);
		} else {
			currentStretch.addSample(instant);
		}
	}

	/**
	 * @return the maxDifference
	 */
	public Duration getMaxDifference() {
		return maxDifference;
	}

	/**
	 * @param maxDifference the maxDifference to set
	 */
	public void setMaxDifference(Duration maxDifference) {
		this.maxDifference = maxDifference;
	}

	/**
	 * @return the list
	 */
	public List<Stretch<T>> getList() {
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String toString() {
		String s = list.stream()
				.map(x-> "\t" + x )
				.collect(
						Collectors.joining(System.lineSeparator()));
		
		return "StretchContainer: ["+ System.lineSeparator() +s+ System.lineSeparator() + "]";
	}
}
