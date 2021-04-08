package com.abpadan.dictionary;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationMain {

	private static List<String> operatorList;
	private DictionaryService dictionaryService = new DictionaryService();

	static {
		operatorList = Stream.of(Operation.values()).map(Enum::name).collect(Collectors.toList());
	}

	public static void main(String[] args) {
		ApplicationMain applicationMain = new ApplicationMain();
		applicationMain.readInput();
	}

	public void readInput() {
		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNext()) {
			String input = scanner.nextLine();
			if ("EXIT".equals(input)) {
				break;
			} else if ("-h".equals(input)) {
				printOperationNames();
			} else {
				String[] inputSplit = input.split(" ");
				String operator = inputSplit[0];
				handleOperator(operator, inputSplit);
			}
		}
		scanner.close();
	}

	private void printOperationNames() {
		for (Operation operation : Operation.values()) {
			System.out.println(operation.name() + " - " + operation.getDescription());
		}
	}

	private void handleOperator(String operator, String[] inputSplit) {
		if (operatorList.contains(operator)) {
			Operation operation = Operation.valueOf(operator);
			if (!handleMinArguments(inputSplit, operation)) {
				System.out.println("Program arguments not met. " + operation.name() + " needs "
						+ operation.getArgumentCount() + " arguments split by space.");
			} else {
				switch (operation) {
				case ADD:
					addMembers(inputSplit[1], inputSplit[2]);
					break;
				case ALLMEMBERS:
					allMembers();
					break;
				case CLEAR:
					clear();
					break;
				case ITEMS:
					getItems();
					break;
				case JSON:
					json();
					break;
				case KEYEXISTS:
					keysExists(inputSplit[1]);
					break;
				case KEYS:
					getKeys();
					break;
				case LOAD:
					load(inputSplit[1]);
					break;
				case MEMBERS:
					getMembers(inputSplit[1]);
					break;
				case REMOVE:
					if (inputSplit.length == 3) {
						removeMember(inputSplit[1], inputSplit[2]);
					} else {
						removeMember(inputSplit[1], null);
					}
					break;
				case REMOVEALL:
					removeAll(inputSplit[1]);
					break;
				case VALUEEXISTS:
					valueExists(inputSplit[1], inputSplit[2]);
					break;
				default:
					break;
				}
			}
		} else {
			System.out.println("Operation not found.");
			printOperationNames();
		}
	}

	private void load(String json) {
		if (dictionaryService.load(json)) {
			System.out.println("Dictionary loaded from JSON.");
		}
	}

	private void json() {
		System.out.println(dictionaryService.printJSON());
	}

	private void getItems() {
		List<String> items = dictionaryService.getItems();
		if (items.isEmpty()) {
			handleEmptySet();
		} else {
			printList(items);
		}
	}

	private void allMembers() {
		List<String> members = dictionaryService.allMembers();
		if (members.isEmpty()) {
			handleEmptySet();
		} else {
			printList(members);
		}
	}

	private void valueExists(String key, String value) {
		System.out.println(dictionaryService.findMembers(key).contains(value));
	}

	private void keysExists(String key) {
		System.out.println(dictionaryService.containsKey(key));
	}

	private void clear() {
		dictionaryService.clear();
		System.out.println("Cleared");
	}

	private void removeAll(String key) {
		if (dictionaryService.containsKey(key)) {
			dictionaryService.removeAll(key);
			System.out.println("Removed");
		} else {
			System.out.println("ERROR, key does not exist");
		}
	}

	private void removeMember(String key, String value) {
		boolean removed = dictionaryService.remove(key, value);
		if (removed) {
			System.out.println("Removed");
		} else {
			System.out.println("ERROR, value does not exist");
		}
	}

	private void addMembers(String key, String value) {
		List<String> members = dictionaryService.findMembers(key);
		if (members != null && members.contains(value)) {
			System.out.println("ERROR, value already exists");
		} else {
			dictionaryService.add(key, value);
			System.out.println("Added");
		}
	}

	private void getMembers(String key) {
		List<String> members = dictionaryService.findMembers(key);
		if (members == null || members.isEmpty()) {
			System.out.println("ERROR, key does not exist.");
		} else {
			printList(members);
		}
	}

	private void handleEmptySet() {
		System.out.println("(empty set)");
	}

	private void getKeys() {
		List<String> keys = dictionaryService.findKeys();
		if (keys.isEmpty()) {
			handleEmptySet();
		} else {
			printList(keys);
		}
	}

	private void printList(List<String> string) {
		for (int i = 0; i < string.size(); i++) {
			System.out.println((i + 1) + ") " + string.get(i));
		}
	}

	private boolean handleMinArguments(String[] inputSplit, Operation operation) {
		// REMOVE has a variable amount of argument lengths
		if (operation == Operation.REMOVE) {
			return (inputSplit.length == 2 || inputSplit.length == 3);
		} else {
			return (inputSplit.length == operation.getArgumentCount());
		}
	}
}
