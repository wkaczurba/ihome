package com.readbacks;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;

import org.junit.Test;

public class StretchContainerTests {

	@Test
	public void stretchContainerTests() {
		StretchContainer<Integer> sc = new StretchContainer<>();
		
		Instant time = Instant.now();
		
		sc.addSample(2, time);
		time = time.plus(Duration.ofMillis(12304));
		sc.addSample(5, time);
		
		
		Assert.assertEquals(2, sc.getList().size());
		Assert.assertEquals(2, sc.getList().size());
		Assert.assertEquals(1, sc.getList().get(0).getSamples());
		Assert.assertEquals(1, sc.getList().get(1).getSamples()); 
		
		// TODO: Tests for:
		// 1. addition to the same Stretch
		// 2. different stretch on timeout
		// 3. change of timeout
	}
	
}
