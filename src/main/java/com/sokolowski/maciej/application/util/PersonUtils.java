package com.sokolowski.maciej.application.util;

import java.util.List;

import com.sokolowski.maciej.application.constants.City;
import com.sokolowski.maciej.application.constants.Consts;
import com.sokolowski.maciej.application.handlers.PersonHandler;
import com.sokolowski.maciej.application.model.Person;

public class PersonUtils {

	/**
	 * Returns modified path provided by user to be able to call other APIs
	 * 
	 * @param city String provided by user as part of URL
	 * @return returns city converted into correct formatting for URI
	 */
	public String adjustCityNameToUrl(String city) {
		city = city.toLowerCase();
		String cityUrl = "/" + city.substring(0, 1).toUpperCase() + city.substring(1);
		return cityUrl;
	}

	/**
	 * Returns distance in miles between chosen city coordinates and person's living coordinates 
	 * 
	 * @param city City enum from where we count the distance
	 * @param latitude double value of person's living latitude value
	 * @param longitude double value of person's living longitude value
	 * @return distance in miles between city and person's living coordinated (int)
	 */
	public int calculateDistanceFromCity(City city, double latitude, double longitude) {
		// Equation for distance between points - sqrt((Xb-Xa)^2+(Yb-Ya)^a) -
		Double distnaceFromCity = Math
				.sqrt(Math.pow((latitude - city.getLatitude()), 2) + Math.pow(longitude - city.getLongitude(), 2));
		// Distance in miles between 1 latitude/longitude is 69miles
		Double distanceFromCityInMiles = distnaceFromCity * 69;
		return (int) Math.round(distanceFromCityInMiles);
	}

	/**
	 * Adds people living in certain city and people around it to one instance
	 * 
	 * @param peopleLivingInCity List of people living in a city
	 * @param allPeople List of all people retrieved from API
	 * @param personHandler instance of PersonHandler to add people living in city and around it
	 * @param city City enum from where we count the distance
	 */
	public void addPeopleLivingAroundCity(List<Person> peopleLivingInCity, List<Person> allPeople, PersonHandler personHandler, City city) {

		// Adding all people living in city
		for (Person p : peopleLivingInCity) {
			personHandler.add(p);
		}
		
		// Checking distance only for people that don't exist in Map yet and living x away from city
		for (Person p : allPeople) {
			if (!personHandler.listAll().containsKey(p.getId()) && new PersonUtils().isLivingCloseToCity(city, p)) {
				personHandler.add(p);
			}
		}
	}

	/**
	 * Returns boolean whether person lives close to the city or not
	 *
	 * @param city City enum from where we count the distance
	 * @param person Person object that distance check has to be performed on
	 * @return returns bool if person lives close enough
	 */
	public boolean isLivingCloseToCity(City city, Person person) {
		PersonUtils utils = new PersonUtils();
		boolean isLivingClose = false;
		int distance = utils.calculateDistanceFromCity(city, person.getLatitude(), person.getLongitude());
		if (distance <= Consts.LIVING_DISTANCE_FROM_CITY) {
			isLivingClose = true;
		}
		return isLivingClose;
	}
}