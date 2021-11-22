package mcServerApp.files;

public class InvalidKeysValueException extends Exception {
	public InvalidKeysValueException(Object value, Keys k) {
		System.err.println("The value <" + value.toString() + "> is invalid for the field <" + k.toString() + ">");
	}
}
