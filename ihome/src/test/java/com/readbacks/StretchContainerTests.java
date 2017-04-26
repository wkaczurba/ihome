package com.readbacks;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;

import org.junit.Test;

public class StretchContainerTests {

	@Test
	public void stretchContainerTest1() {
		StretchContainer<Integer> sc = new StretchContainer<>();
		
		Instant time = Instant.now();
		
		sc.addSample(2, time);
		time = time.plus(Duration.ofMillis(12304));
		sc.addSample(5, time);
		
		
		Assert.assertEquals(2, sc.getList().size());
		Assert.assertEquals(2, sc.getList().size());
		Assert.assertEquals(1, sc.getList().get(0).getCount());
		Assert.assertEquals(1, sc.getList().get(1).getCount()); 
		
		System.out.println(sc);
	}
	
	@Test
	public void stretchContainerAddingToTheSameStretchTest() {
		Assert.fail("Unimplemented");
	}
	
	@Test
	public void stretchContainerAddingToDifferentStretchOnTimeout() {
		Assert.fail("Unimplemented");
	}
	
	@Test
	public void stretchContainerChangeTimeout() {
		Assert.fail("Unimplemented");
	}


	// TODO: Tests for:
	// 1. addition to the same Stretch
	// 2. different stretch on timeout
	// 3. change of timeout
	
}
