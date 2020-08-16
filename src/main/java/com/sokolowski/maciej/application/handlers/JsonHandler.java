package com.sokolowski.maciej.application.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sokolowski.maciej.application.model.Person;

public class JsonHandler {

	/**
	 * Method responsible for parsing JSON object retrieved from API and mapping it
	 * to object Person
	 * 
	 * @param response CloseableHttpResponse retrieved from API call
	 * @return returns List<Person> with retrieved and mapped data from API
	 */
	public List<Person> parseJson(CloseableHttpResponse response) {
		HttpEntity entity = response.getEntity();
		ObjectMapper mapper = new ObjectMapper();
		List<Person> listResults = new ArrayList<Person>();
		// Enables handling single value as array
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		if (entity != null) {
			InputStream inputStrem;
			try {
				inputStrem = entity.getContent();

				// Maps JSON values to List<Person>
				listResults = mapper.readValue(inputStrem, new TypeReference<List<Person>>() {
				});
			} catch (UnsupportedOperationException | IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listResults;
	}
}
