package com.readbacks;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.junit.Assert;

public class StretchTests {

	@Test
	public void emptyStretchTest() {
		Instant time = Instant.now(); 
		
		Stretch<Double> s = new Stretch<>(3.0, time);
		try {
			s.getMaxDifference();
			throw new RuntimeException("It should have failed.");
		} catch (UnsupportedOperationException e) { }

		try {
			s.getMinDifference();
			throw new RuntimeException("It should have failed.");
		} catch (UnsupportedOperationException e) { }

		try {
			s.averagePeriod();
			throw new RuntimeException("It should have failed.");
		} catch (UnsupportedOperationException e) { }
		
		Assert.assertEquals(1L, s.getSamples());
		Assert.assertEquals((Double) 3.0, s.getValue());
		
		
		List<Long> delays = new ArrayList<>();
		for (int i=0; i<100; i++) {
			long seconds = (long) (Math.random() * 100);
			delays.add(seconds);
			
			time = time.plus(Duration.ofSeconds(seconds));			
			s.addSample(time);
		}
		
		Assert.assertEquals(s.getMaxDifference(), Duration.ofSeconds((long) delays.stream().mapToDouble(x -> x).max().getAsDouble()));
		Assert.assertEquals(s.getMinDifference(), Duration.ofSeconds((long) delays.stream().mapToDouble(x -> x).min().getAsDouble()));
		Assert.assertEquals(s.averagePeriod(), Duration.ofMillis((long) (delays.stream().mapToDouble(x -> x).average().getAsDouble()*1000)));
		Assert.assertEquals(s.getPeriodEnded(), time);
		Assert.assertEquals(s.getPeriodStarted(), time.minus(Duration.ofMillis((long) (delays.stream().mapToDouble(x -> x).sum() * 1000))));
	}
	
	
}
