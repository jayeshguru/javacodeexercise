package com.java.test.mbta;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

public class MBTARoutesTest {

	@Test
	public void TestDisplayRoutes() {
		MBTARoutes mbtaRoutes = new MBTARoutes();
		List<String> routeList = mbtaRoutes.GetRoutes();
		assertEquals(routeList.size(), 8);

		System.out.println("");
		System.out.println("======Exercise 1 Display All Routes with type 0,1 ======");
		for (String route : routeList) {
			System.out.println(route);
		}
	}
	
	@Test
	public void TestDisplayRouteWithMost_Least_Stops() {
		MBTARoutes mbtaRoutes = new MBTARoutes();
		TreeMap<String, Integer> sortedStops = mbtaRoutes.DisplayRouteWithMost_Least_Stops();
		assertEquals(sortedStops.firstEntry().toString(), "Green Line B=24");
		assertEquals(sortedStops.lastEntry().toString(), "Mattapan Trolley=8");
	}

	@Test
	public void TestDisplayStopsConnectingMostRoutes() {
		MBTARoutes mbtaRoutes = new MBTARoutes();
		mbtaRoutes.DisplayStopsConnectingMostRoutes();
	}

	@Test
	public void TestGetRoutesByStopId() {
		MBTARoutes mbtaRoutes = new MBTARoutes();
		mbtaRoutes.getRoutesByStopId("place-haecl");
	}
	
	@Test
	public void TestGetStopsByRouteId() {
		MBTARoutes mbtaRoutes = new MBTARoutes();
		mbtaRoutes.getStopsByRouteId("Red");
	}

}
