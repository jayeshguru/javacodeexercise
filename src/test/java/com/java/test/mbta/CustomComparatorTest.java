package com.java.test.mbta;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class CustomComparatorTest {

	@Test
	public void testCustomComparator() {
		HashMap<String,Integer> hmRouteWithStops = new HashMap<String,Integer>();
		hmRouteWithStops.put("GreenLine", 38);
		CustomComparator cus = new CustomComparator(hmRouteWithStops);
		assertTrue(cus.map.get("GreenLine")==38);
	}

	@Test
	public void testCompare() {
		HashMap<String,Integer> hmRouteWithStops = new HashMap<String,Integer>();
		hmRouteWithStops.put("RedLine", 24);
		hmRouteWithStops.put("BlueLine", 21);
		hmRouteWithStops.put("GreenLine", 38);
		hmRouteWithStops.put("OrangeLine", 12);
		hmRouteWithStops.put("MattappanTrolley", 10);
		
		CustomComparator cus = new CustomComparator(hmRouteWithStops);
		int comparison = cus.compare("RedLine","GreenLine");
		assertTrue(comparison==1);
		
		comparison = cus.compare("RedLine","BlueLine");
		assertTrue(comparison==-1);
	}

}
