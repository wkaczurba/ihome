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
			s.getMaxDelay();
			throw new RuntimeException("It should have failed.");
		} catch (UnsupportedOperationException e) { }

		try {
			s.getMinDelay();
			throw new RuntimeException("It should have failed.");
		} catch (UnsupportedOperationException e) { }

		try {
			s.averageDelay();
			throw new RuntimeException("It should have failed.");
		} catch (UnsupportedOperationException e) { }
		
		Assert.assertEquals(1L, s.getCount());
		Assert.assertEquals((Double) 3.0, s.getValue());
		
		
		List<Long> delays = new ArrayList<>();
		for (int i=0; i<100; i++) {
			long miliseconds = (long) (Math.random() * 100) * 1000;
			delays.add(miliseconds);
			
			time = time.plus(Duration.ofMillis(miliseconds));			
			s.addSample(time);
		}
		
		Assert.assertEquals(s.getMaxDelay(), delays.stream().mapToLong(x -> x).max().getAsLong());
		Assert.assertEquals(s.getMinDelay(), delays.stream().mapToLong(x -> x).min().getAsLong());
		Assert.assertEquals(s.averageDelay(), (long) (delays.stream().mapToLong(x -> x).average().getAsDouble() ));
		Assert.assertEquals(s.getEnd(), time);
		Assert.assertEquals(s.getStart(), time.minus(Duration.ofMillis((long) (delays.stream().mapToDouble(x -> x).sum()))));
	}
	
	
}
