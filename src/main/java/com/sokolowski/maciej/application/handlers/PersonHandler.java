package com.sokolowski.maciej.application.handlers;

import java.util.TreeMap;

import com.sokolowski.maciej.application.model.Person;

public class PersonHandler {

	//Using map to make sure that people are not duplicated, without using additional checks
	private TreeMap<Integer, Person> listOfAllPeople = new TreeMap<Integer, Person>();
	
	public TreeMap<Integer, Person> listAll() {
		return listOfAllPeople;
	}
	
	public void add(Person person) {
		if(person!=null) {
			listOfAllPeople.put(person.getId(), person);
		}
	}
}
