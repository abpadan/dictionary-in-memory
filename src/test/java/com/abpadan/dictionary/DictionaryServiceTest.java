package com.abpadan.dictionary;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DictionaryServiceTest {

	private DictionaryService dictionaryService;
	private String key1 = "foo";
	private String key1Value1 = "bar";
	private String key1Value2 = "baz";
	private String key2 = "goo";
	private String key2Value1 = "tar";
	private String key2Value2 = "taz";

	@Before
	public void init() {
		dictionaryService = new DictionaryService();
		dictionaryService.add(key1, key1Value1);
		dictionaryService.add(key1, key1Value2);
		dictionaryService.add(key2, key2Value1);
		dictionaryService.add(key2, key2Value2);
	}

	@Test
	public void testAdd() {
		assertEquals(false, dictionaryService.isEmpty());
		assertEquals(true, dictionaryService.containsKey(key1));
		assertEquals(true, dictionaryService.containsKey(key2));
		List<String> membersKeys1 = new ArrayList<>();
		membersKeys1.add(key1Value1);
		membersKeys1.add(key1Value2);
		List<String> membersKeys2 = new ArrayList<>();
		membersKeys2.add(key2Value1);
		membersKeys2.add(key2Value2);
		assertEquals(membersKeys1, dictionaryService.findMembers(key1));
		assertEquals(membersKeys2, dictionaryService.findMembers(key2));
	}

	@Test
	public void testRemove() {
		dictionaryService.remove(key1, key1Value1);
		assertEquals(false, dictionaryService.isEmpty());
		dictionaryService.remove(key1, key1Value2);
		assertEquals(false, dictionaryService.containsKey(key1));
		dictionaryService.remove(key2, key2Value1);
		assertEquals(true, dictionaryService.containsKey(key2));
		dictionaryService.remove(key2, key2Value2);
		assertEquals(true, dictionaryService.isEmpty());
	}

	@Test
	public void testFindKeys() {
		List<String> keys = dictionaryService.findKeys();
		assertEquals(true, !keys.isEmpty());
		assertEquals(true, keys.contains(key1));
		assertEquals(true, keys.contains(key2));
	}

	@Test
	public void testContainsKey() {
		assertEquals(true, dictionaryService.containsKey(key1));
		assertEquals(true, dictionaryService.containsKey(key2));
	}

	@Test
	public void testMembers() {
		assertEquals(true, dictionaryService.findMembers(key1).contains(key1Value1));
		assertEquals(true, dictionaryService.findMembers(key1).contains(key1Value2));
		assertEquals(true, dictionaryService.findMembers(key2).contains(key2Value1));
		assertEquals(true, dictionaryService.findMembers(key2).contains(key2Value2));
	}
	
	@Test
	public void testRemoveAll() {
		dictionaryService.removeAll(key1);
		assertEquals(false, dictionaryService.findKeys().contains(key1));
		dictionaryService.removeAll(key2);
		assertEquals(false, dictionaryService.findKeys().contains(key2));
	}
	
	@Test
	public void testIsEmpty() {
		dictionaryService.clear();
		assertEquals(true, dictionaryService.isEmpty());
	}
}
