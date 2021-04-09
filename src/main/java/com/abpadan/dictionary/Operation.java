package com.abpadan.dictionary;

public enum Operation {
	
	ADD("Add a member to a collection for a given key.", 3),
	ALLMEMBERS("Returns all the values in the dictionary.", 1),
	CLEAR("Removes all keys and all values from the dictionary.", 1),
	ITEMS("Returns all keys in the dictionary and all of their values.", 1),
	JSON("Prints the dictionary as JSON.", 1),
	KEYEXISTS("Returns whether a key exists or not.", 2), 
	KEYS("Returns all the keys in the dictionary.", 1),
	LOAD("Loads an existing JSON dictionary into memory.", 2),
	MEMBERS("Returns the collection of strings for the given key.", 2),
	REMOVE("Removes a value from a key.", 2),
	REMOVEALL("Removes all value for a key and removes the key from the dictionary.", 2),
	VALUEEXISTS("Returns whether a value exists within a key.", 3);
	
	private Integer argumentCount;
	private String description;

	private Operation(String description, Integer argumentCount) {
		this.description = description;
		this.argumentCount = argumentCount;
	}

	public Integer getArgumentCount() {
		return argumentCount;
	}

	public String getDescription() {
		return description;
	}
}
