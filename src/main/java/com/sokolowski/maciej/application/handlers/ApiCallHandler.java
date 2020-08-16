package com.sokolowski.maciej.application.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.ws.rs.core.Response;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.sokolowski.maciej.application.model.Person;

public class ApiCallHandler {

	/**
	 * This method is responsible for retrieving API data - calling methods to
	 * connect to URL, map results and return list of people
	 * 
	 * @param url String url required to connect to API
	 * @return returns List<Person> with retrieved and mapped data from API
	 */
	public List<Person> retrieveApiData(String url) {
		JsonHandler jsonHandler = new JsonHandler();
		ClientConnectionHandler clientConnectionHandler = new ClientConnectionHandler();

		CloseableHttpResponse response = clientConnectionHandler.sendGet(url);
		boolean isOk = false;
		List<Person> listResults = new ArrayList<Person>();

		// Check if API call returned OK and only then continue
		isOk = clientConnectionHandler.isResponseOk(response);
		if (isOk) {
			listResults = jsonHandler.parseJson(response);
		}
		clientConnectionHandler.close();
		return listResults;
	}

	/**
	 * Builds response for API to return to user
	 * 
	 * @param mapResults TreeMap<Integer, Person> of data to be returned to user
	 * @return returns appropriate response depending on the content of data
	 */
	public Response apiResponse(TreeMap<Integer, Person> mapResults) {
		Response apiResponse = null;
		if (mapResults == null || mapResults.isEmpty()) {
			apiResponse = Response.noContent().build();
		} else {
			apiResponse = Response.ok(mapResults.values()).build();
		}
		return apiResponse;
	}
}
