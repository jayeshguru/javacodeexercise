package com.java.test.mbta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.java.route.model.MBTARoutesModel;
import com.java.route.model.Route;
import com.java.stop.model.MBTAStopsModel;
import com.java.stop.model.Stop;

public class MBTARoutes {

	// Coding Exercise 1
	// This method Display Routes' LongNames
	public List<String> GetRoutes() {

		List<String> routeList = new ArrayList<String>();

		try {
			// Getting List of routes to iterate over for getting LongNames
			List<Route> routes = GetRoutesList();

			// Iterate over the routes to display long names of the routes
			if (routes != null && routes.size() > 0) {
				for (Route route : routes) {
					routeList.add(route.getAttributes().getLong_name());
				}
			} else {
				// If No routes are present, Error out with System Error
				System.err.println("Could not get routes from response");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return routeList;
	}

	// Coding Exercise 2a and 2b
	// This method lists routes with most and least stops
	public TreeMap<String, Integer> DisplayRouteWithMost_Least_Stops() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>();
		try {
			List<Route> routes = GetRoutesList();
			for (Route route : routes) {
				HashSet<String> stops = getStopsByRouteId(route.getId());
				map.put(route.getAttributes().getLong_name(), stops.size());
			}

			sortedMap = sortByStops(map);
			// Display subway route with the most stops along with count of stops
			System.out.println("");
			System.out.println("======Exercise 2a Route With Most Stops ======");
			System.out.println(sortedMap.firstEntry());

			// Display subway route with the least stops along with count of stops
			System.out.println("");
			System.out.println("======Exercise 2b Route With Least Stops ======");
			System.out.println(sortedMap.lastEntry());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sortedMap;
	}

	public List<String> DisplayStopsConnectingMostRoutes() {

		HashSet<String> uniqueStopIds = new HashSet<String>();
		List<Stop> stops;
		List<String> resultList = new ArrayList<String>();

		try {
			stops = GetStopsList();
			for (Stop stop : stops) {
				uniqueStopIds.add(stop.getAttributes().getName() + "_"
						+ stop.getRelationships().getParent_station().getData().get("id"));
			}
			System.out.println("");
			System.out.println("======Exercise 2c Stops connecting 2 or more routes ======");
			for (String uniqueStopId : uniqueStopIds) {
				HashSet<String> routesList = getRoutesByStopId(uniqueStopId.split("_")[1]);
				if (routesList.size() > 1) {
					resultList.add(uniqueStopId.split("_")[0] + "==>" + routesList);
					System.out.println(uniqueStopId.split("_")[0] + "==>" + routesList);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	// This method sorts by Value for HashMap which has route as key and number of
	// stops as value
	public TreeMap<String, Integer> sortByStops(HashMap<String, Integer> routeStops) {

		CustomComparator customComparator = new CustomComparator(routeStops);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(customComparator);
		sortedMap.putAll(routeStops);

		return sortedMap;
	}

	// This method returns routes by input stopId and route type as 0 or 1
	public HashSet<String> getRoutesByStopId(String stopId) {
		String apiUrl = Constants.GET_ROUTES_BY_STOPID + stopId;
		HashSet<String> routesList = new HashSet<String>();

		try {
			// Call API with above URL and get the response back as a StringBuffer
			StringBuffer response = HttpGetCall(apiUrl);

			// Use of ObjectMapper to model the response to Object
			ObjectMapper mapper = new ObjectMapper();

			mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

			// This block of code maps the response to the object model
			MBTARoutesModel mbtaRoutesData = mapper.readValue(response.toString(), MBTARoutesModel.class);

			// Getting List of routes to iterate over for getting LongNames
			List<Route> routes = mbtaRoutesData.getData();

			// Iterate over the routes to display long names of the routes
			if (routes != null && !routes.isEmpty() && routes.size() > 0) {
				for (Route route : routes) {
					routesList.add(route.getAttributes().getLong_name());
				}
			} else {
				// If No routes are present, Error out with System Error
				System.err.println("Could not get routes from response");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return routesList;
	}

	// This method returns Stops with with input RouteId
	public HashSet<String> getStopsByRouteId(String routeId) {
		String apiUrl = Constants.GET_STOPS_BY_ROUTEID + routeId;
		HashSet<String> stopsList = new HashSet<String>();

		try {
			// Call API with above URL and get the response back as a StringBuffer
			StringBuffer response = HttpGetCall(apiUrl);

			// Use of ObjectMapper to model the response to Object
			ObjectMapper mapper = new ObjectMapper();

			mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

			// This block of code maps the response to the object model
			MBTAStopsModel mbtaStopsData = mapper.readValue(response.toString(), MBTAStopsModel.class);

			// Getting List of routes to iterate over for getting LongNames
			List<Stop> stops = mbtaStopsData.getData();

			// Iterate over the routes to display long names of the routes
			if (stops != null && !stops.isEmpty() && stops.size() > 0) {
				for (Stop stop : stops) {
					stopsList.add(stop.getAttributes().getName());
					// System.out.println(stop.getAttributes().getName());
				}
			} else {
				// If No routes are present, Error out with System Error
				System.err.println("Could not get routes from response");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stopsList;
	}

	// This method returns all Routes with RouteType as 0 or 1
	public List<Route> GetRoutesList() throws MalformedURLException, IOException, Exception {
		// ApiURL to display Subway Route Details, this is filtered by Type 0 =
		// LightRail and Type 1 = Heavy Rail
		String apiUrl = Constants.GET_ALL_ROUTES;

		// Call API with above URL and get the response back as a StringBuffer
		StringBuffer response = HttpGetCall(apiUrl);

		// Use of ObjectMapper to model the response to Object
		ObjectMapper mapper = new ObjectMapper();

		// Disable the feature that determines whether encountering of unknown
		// properties (ones that do not map to a property, and there is
		// no "any setter" or handler that can handle it) should result in a failure
		// This is disabled in order to use the only variables needed for our program in
		// model and not all
		mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

		// This block of code maps the response to the object model
		MBTARoutesModel mbtaRoutesData = mapper.readValue(response.toString(), MBTARoutesModel.class);

		// Getting List of routes to iterate over for getting LongNames
		List<Route> routes = mbtaRoutesData.getData();

		return routes;
	}

	// This method returns all Stops with RouteType as 0 or 1
	public List<Stop> GetStopsList() throws MalformedURLException, IOException, Exception {
		// ApiURL to display Subway Route Details, this is filtered by Type 0 =
		// LightRail and Type 1 = Heavy Rail
		String apiUrl = Constants.GET_ALL_STOPS;

		// Call API with above URL and get the response back as a StringBuffer
		StringBuffer response = HttpGetCall(apiUrl);

		// Use of ObjectMapper to model the response to Object
		ObjectMapper mapper = new ObjectMapper();

		// Disable the feature that determines whether encountering of unknown
		// properties (ones that do not map to a property, and there is
		// no "any setter" or handler that can handle it) should result in a failure
		// This is disabled in order to use the only variables needed for our program in
		// model and not all
		mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

		// This block of code maps the response to the object model
		MBTAStopsModel mbtaStopsData = mapper.readValue(response.toString(), MBTAStopsModel.class);

		// Getting List of routes to iterate over for getting LongNames
		List<Stop> stops = mbtaStopsData.getData();

		return stops;
	}

	// This method calls API with given URL and returns response
	public StringBuffer HttpGetCall(String apiUrl) throws MalformedURLException, Exception {

		StringBuffer response = new StringBuffer();
		String inputLine;

		URL url = new URL(apiUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(Constants.HTTP_METHODGET);
		// Using apiKey as a Header to avoid getting Rate Limited
		con.setRequestProperty(Constants.REQUEST_HEADER_X_API_KEY, Constants.REQUEST_HEADERVALUE_X_API_KEY);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();
		return response;
	}
}
