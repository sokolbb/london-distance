package com.sokolowski.maciej.application.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

//@RequestScoped
public class ClientConnectionHandler {
	
	CloseableHttpClient httpClient = HttpClients.createDefault();
	
	/**
	 * Https/Http connection to the provided URL (with headers)
	 * 
	 * @param url String
	 * @param headers HashMap<String, String> that might need providing to connect to URL
	 * @return Response
	 */
	// Hashmap stores headers as <header, value>
	public CloseableHttpResponse sendGet(String url, HashMap<String, String> headers) {
		
		HttpGet request = new HttpGet(url);
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				request.addHeader(header.getKey(), header.getValue());
			}
		}
		CloseableHttpResponse result = null;

		try {
			CloseableHttpResponse response = httpClient.execute(request);
			result = response;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Https/Http connection to the provided URL (no headers)
	 * 
	 * @param url String
	 * @return Response
	 */
	public CloseableHttpResponse sendGet(String url) {
		
		HttpGet request = new HttpGet(url);
		CloseableHttpResponse result = null;

		try {
			CloseableHttpResponse response = httpClient.execute(request);
			result = response;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Checks if response of server is 200
	 * 
	 * @param response CloseableHttpResponse retrieved from API call
	 * @return returns boolean if response was OK or not
	 */
	public boolean isResponseOk(CloseableHttpResponse response) {
		boolean isOk = false;
		// Only continue with checking status of a response if response is not null and
		// status is not null, otherwise response is not ok
		if (response != null && response.getStatusLine() != null) {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				isOk = true;
			}
		}
		return isOk;
	}

	/**
	 * Method responsible closing CloseableHttpClient
	 */
	public void close() {
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
