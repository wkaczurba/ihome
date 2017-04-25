package com.readbacks;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class Stretch<T> {
	//double averageDifference;
	private Duration maxDifference;
	/**
	 * @return the maxDifference
	 */
	public Duration getMaxDifference() {
		if (samples < 2) {
			throw new UnsupportedOperationException("At least 2 samples needed to get maxDifference");
		}
		return maxDifference;
	}

	/**
	 * @return the minDifference
	 */
	public Duration getMinDifference() {
		if (samples < 2) {
			throw new UnsupportedOperationException("At least 2 samples needed to get minDifference");
		}
		return minDifference;
	}

	/**
	 * @return the samples
	 */
	public long getSamples() {
		return samples;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @return the periodStarted
	 */
	public Instant getPeriodStarted() {
		return start;
	}

	/**
	 * @return the periodEnded
	 */
	public Instant getPeriodEnded() {
		return end;
	}


	private Duration minDifference;
	private long samples;
	
	private T value;
	private Instant start;
	private Instant end;
	
	public Stretch(T value, Instant instant) {
		start = instant;
		end = instant;
		samples = 1;
		this.value = value;
	}
	
	public void addSample(Instant instant) {
		Duration timeDiff = null;
		
		if (instant.isBefore(end)) {
			throw new IllegalArgumentException("instant < periodEnded; only consecutive order is permitted.");
		} 
		
		timeDiff = Duration.between(end,  instant);
		end = instant;
		if (maxDifference == null || timeDiff.compareTo(maxDifference) > 0) {
			maxDifference = timeDiff;
		}
		if (minDifference == null || timeDiff.compareTo(minDifference) < 0) {
			minDifference = timeDiff;
		}
		samples += 1;
	}
	
	public Duration averagePeriod() {
		if (samples < 2) {
			throw new UnsupportedOperationException("At least 2 samples needed to get averagedDifference");
		}
		return Duration.between(start, end).dividedBy(samples - 1);
	}

	
	@Override
	public String toString() {
		if (samples < 2)
			return String.format("SamplingStretch: value=%d [t=<%s - %s> %d samples]", 
					value, start, end, samples);
					
		DateTimeFormatter formatter;
			//formatter = DateTimeFormatter
				//.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
				//.withZone(ZoneId.systemDefault());
		//formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault());
		//formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault());
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
		
		return String.format("SamplingStretch: value=%d [t=<%s-%s> %d samples; avg=%d ms, min=%d ms, max=%d ms]", 
				value, 
				formatter.format(start),
				formatter.format(end), 
				samples, 
				averagePeriod().toMillis(), 
				minDifference.toMillis(),
				maxDifference.toMillis());
	}
}
