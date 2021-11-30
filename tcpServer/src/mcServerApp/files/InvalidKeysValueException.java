package mcServerApp.files;

public class InvalidKeysValueException extends Exception {
	public InvalidKeysValueException(Object value, Keys k) {
		System.err.println("The value <" + value.toString() + "> isn't available for the field <" + k.toString() + ">");
	}
	
	public InvalidKeysValueException() {
		System.err.println("Invalid value given for the type " + Keys.class);
	}
}
