package com.sokolowski.maciej.application.constants;

public final class Consts {
	
	private Consts(){
		//this prevents any class, even native from calling constructor
		throw new AssertionError();
	}
	
	//API connection details
	public final static String BPDTS_URL_ADDRESS = "https://bpdts-test-app-v3.herokuapp.com";
	public final static String USERS_API_URI = 	"/users";
	public final static String SINGLE_USER_API_URI = "/user";
	public final static String CITY_API_URI = "/city";
	
	//Distance from far away we we should include people
	public final static int LIVING_DISTANCE_FROM_CITY = 60;
}
