package com.readbacks;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class Stretch<T> {
	//double averageDifference;
	private long maxDelay = -1;
	private long minDelay = -1;
	private long count;
	
	private T value;
	private Instant start;
	private Instant end;
	
	/**
	 * @return the maxDifference
	 */
	public long getMaxDelay() {
		if (count < 2) {
			throw new UnsupportedOperationException("At least 2 samples needed to get maxDifference");
		}
		return maxDelay;
	}

	/**
	 * @return the minDifference
	 */
	public long getMinDelay() {
		if (count < 2) {
			throw new UnsupportedOperationException("At least 2 samples needed to get minDifference");
		}
		return minDelay;
	}

	/**
	 * @return the samples
	 */
	public long getCount() {
		return count;
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
	public Instant getStart() {
		return start;
	}

	/**
	 * @return the periodEnded
	 */
	public Instant getEnd() {
		return end;
	}

	public Stretch(T value, Instant instant) {
		start = instant;
		end = instant;
		count = 1;
		this.value = value;
	}
	
	public void addSample(Instant instant) {
		long timeDiff;
		
		if (instant.isBefore(end)) {
			throw new IllegalArgumentException("instant < periodEnded; only consecutive order is permitted.");
		} 
		
		timeDiff = Duration.between(end,  instant).toMillis();
		end = instant;
		if (maxDelay == -1 || timeDiff > maxDelay) {
			maxDelay = timeDiff;
		}
		if (minDelay == -1 || timeDiff < minDelay) {
			minDelay = timeDiff;
		}
		count += 1;
	}
	
	public long averageDelay() {
		if (count < 2) {
			throw new UnsupportedOperationException("At least 2 samples needed to get averagedDifference");
		}
		return Duration.between(start, end).dividedBy(count - 1).toMillis();
	}

	
	@Override
	public String toString() {
		if (count < 2)
			return String.format("SamplingStretch: value=%d [t=<%s - %s> %d samples]", 
					value, start, end, count);
					
		DateTimeFormatter formatter;
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
		
		return String.format("SamplingStretch: value=%d [t=<%s-%s> %d samples; avg=%d ms, min=%d ms, max=%d ms]", 
				value, 
				formatter.format(start),
				formatter.format(end), 
				count, 
				averageDelay(), 
				minDelay,
				maxDelay);
	}
}
