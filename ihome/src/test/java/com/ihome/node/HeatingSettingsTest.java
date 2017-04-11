package com.ihome.node;

import org.junit.Assert;
import org.junit.Test;

public class HeatingSettingsTest {

	@Test
	public void toStringTest() {
		HeatingSettings hs = HeatingSettings.createRandom(0);
		System.out.println( hs );
	}
	
//	@Test
//	public void heatingTest1() {
//		
//		boolean hasZones = false;
//		
//		for (int i = 0; i < 100; i++) {
//			HeatingSettings hs = HeatingSettings.createRandom();
//			HeatingSettings copy = new HeatingSettings(hs);
//			hasZones |= (hs.getZones().size() > 0);
//			Assert.assertEquals(hs, copy);
//			Assert.assertEquals(copy, hs);
//			Assert.assertTrue(copy.equals(hs));
//			Assert.assertTrue(hs.equals(copy));
//		}
//		
//		Assert.assertEquals(true, hasZones);
//		
//	}
	
	

}


