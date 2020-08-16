package com.sokolowski.maciej.application.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sokolowski.maciej.application.constants.City;
import com.sokolowski.maciej.application.constants.Consts;
import com.sokolowski.maciej.application.handlers.ApiCallHandler;
import com.sokolowski.maciej.application.handlers.PersonHandler;
import com.sokolowski.maciej.application.model.Person;
import com.sokolowski.maciej.application.util.PersonUtils;



@Path("/{city}")
public class PersonEndpoint {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response peopleLivingAroundCity(@PathParam("city") String city) {
		ApiCallHandler apiCaller = new ApiCallHandler();
		PersonUtils utils = new PersonUtils();
		PersonHandler personHandler = new PersonHandler();
		Response apiResponse = null;

		// It only allows to check for London at the moment as that's the only added
		// city with location
		City cityToCompare = null;
		boolean isCityKnown = false;
		for (City c : City.values()) {
			if (c.getName().equalsIgnoreCase(city)) {
				isCityKnown = true;
				cityToCompare = c;
				break;
			}
		}
		if (isCityKnown) {
			// Call to API to retrieve people living in city
			city = utils.adjustCityNameToUrl(city);
			String url = Consts.BPDTS_URL_ADDRESS + Consts.CITY_API_URI + city + Consts.USERS_API_URI;
			List<Person> peopleLivingInCity = apiCaller.retrieveApiData(url);

			// Call to API to retrieve all people
			url = Consts.BPDTS_URL_ADDRESS + Consts.USERS_API_URI;
			List<Person> allPeople = apiCaller.retrieveApiData(url);

			utils.addPeopleLivingAroundCity(peopleLivingInCity, allPeople, personHandler, cityToCompare);

			apiResponse = apiCaller.apiResponse(personHandler.listAll());
		} else {
			apiResponse = Response.status(Response.Status.BAD_REQUEST).build();
		}
		return apiResponse;
	}
}
