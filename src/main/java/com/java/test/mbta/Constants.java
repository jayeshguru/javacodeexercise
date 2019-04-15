/**
 * 
 */
package com.java.test.mbta;

/**
 * @author Jayesh
 *
 */
public class Constants {
	
	//API V3 MBTA Endpoint URLs
	public static final String GET_ROUTES_BY_STOPID = "https://api-v3.mbta.com/routes?filter[type]=0,1&filter[stop]=";
	public static final String GET_STOPS_BY_ROUTEID = "https://api-v3.mbta.com/stops?filter[route]=";
	public static final String GET_ALL_ROUTES = "https://api-v3.mbta.com/routes?filter[type]=0,1";
	public static final String GET_ALL_STOPS = "https://api-v3.mbta.com/stops?filter[route_type]=0,1";
	
	//Constants for HTTP Calls
	public static final String HTTP_METHODGET = "GET";
	public static final String REQUEST_HEADER_X_API_KEY = "x-api-key";
	public static final String REQUEST_HEADERVALUE_X_API_KEY= "26da7aae1d464b0eb025171ee4277ddc";

}
