package com.abpadan.dictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DictionaryService {

	private Map<String, List<String>> dictionary = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
	
	public List<String> findKeys() {
		return new ArrayList<>(dictionary.keySet());
	}
	
	public boolean containsKey(String key) {
		return dictionary.containsKey(key);
	}

	public List<String> findMembers(String key) {
		return dictionary.get(key);
	}

	public boolean add(String key, String value) {
		boolean toAdd = false;
		if (containsKey(key)) {
			if (!dictionary.get(key).contains(value)) {
				toAdd = true;
			}
		} else {
			toAdd = true;
		}

		if (toAdd) {
			List<String> members = dictionary.get(key);
			if (members == null) {
				members = new ArrayList<>();
			}
			members.add(value);
			dictionary.put(key, members);
		}
		return toAdd;
	}

	public boolean remove(String key, String value) {
		boolean removed = false;

		if (dictionary.containsKey(key)) {
			List<String> values = dictionary.get(key);
			if (values != null && values.contains(value)) {			
				if (values.size() == 1) {
					dictionary.remove(key);
					removed = true;
				} else {
					values.remove(value);
					dictionary.put(key, values);
					removed = true;
				}
			}
		}
		return removed;
	}
	
	public void removeAll(String key) {
		dictionary.remove(key);
	}
	
	public void clear() {
		dictionary.clear();
	}
	
	public boolean isEmpty() {
		return dictionary.isEmpty();
	}
	
	public List<String> allMembers() {
		List<String> keys = findKeys();
		List<String> retVal = new ArrayList<>();
		for (String key : keys) {
			List<String> members = findMembers(key);
			retVal.addAll(members);
		}
		return retVal;
	}
	
	public List<String> getItems() {
		List<String> keys = findKeys();
		List<String> items = new ArrayList<>();
		for (String key : keys) {
			List<String> members = findMembers(key);
			for (String member : members) {
				items.add(key + ": " + member);
			}
		}
		return items;
	}
	
	public String printJSON() {
		String jsonDictionary = null;
		try {
			jsonDictionary = objectMapper.writeValueAsString(dictionary);
		} catch (JsonProcessingException e) {
			System.out.println("Error creating JSON string from dictionary, " + e);
		}
		return jsonDictionary;
	}
	
	public boolean load(String json) {
		try {
			@SuppressWarnings("unchecked")
			Map<String, List<String>> loadedMap =
			        objectMapper.readValue(json, HashMap.class);
			dictionary = new HashMap<>(loadedMap);
			return true;
		} catch (JsonParseException e) {
			System.out.println("Json parsing exception when loading JSON, " + e);
		} catch (IOException e) {
			System.out.println("Io exception when loading JSON, " + e);
		}
		return false;
	}
}
