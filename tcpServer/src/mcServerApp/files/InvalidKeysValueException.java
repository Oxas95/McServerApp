package mcServerApp.files;

public class InvalidKeysValueException extends Exception {
	public InvalidKeysValueException(Object value, Keys k) {
		System.err.println("The value <" + value.toString() + "> isn't available for the field <" + k.toString() + ">");
	}
}
