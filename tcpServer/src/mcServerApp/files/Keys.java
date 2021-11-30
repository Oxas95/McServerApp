package mcServerApp.files;

import java.io.File;
import java.math.BigDecimal;

public enum Keys {
	appPort("app port", 0) {
		@Override
		public boolean check(Object value) {
			if(Number.class.isInstance(value)) {
				//verifier que c'est un nombre entier en utilisant le type double
				Double doubleValue = new BigDecimal(value + "").doubleValue();
				if(doubleValue > 1000 && doubleValue < 65535 && doubleValue % 1 == 0)
					return true;
			}
			return false;
		}

		@Override
		public Object parse(String value) throws InvalidKeysValueException {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				throw new InvalidKeysValueException(value, this);
			}
		}
	},
	autoStart("starts by launching the server", false) {
		@Override
		public boolean check(Object value) {
			if(value.getClass() == Boolean.class || value.getClass() == boolean.class)
				return true;
			return false;
		}

		@Override
		public Object parse(String value) throws InvalidKeysValueException {
			try {
				if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))
					return Boolean.parseBoolean(value);
				else
					throw new Exception(); // aller dans le catch
			} catch (Exception e) {
				throw new InvalidKeysValueException(value, this);
			}
		}
	},
	batchPath("batch path", Configuration.none) {
		@Override
		public boolean check(Object value) {
			if(value.getClass() == String.class) {
				File f = new File(((String) value).replace("/", File.separator));
				return f.exists() && f.isFile();
			} else return false;
		}

		@Override
		public Object parse(String value) {
			return value;
		}
	},
	noGui("no window", false) {
		@Override
		public boolean check(Object value) {
			return autoStart.check(value);
		}

		@Override
		public Object parse(String value) throws InvalidKeysValueException {
			return autoStart.parse(value);
		}
	},
	rconPassword("rcon password", Configuration.none) {
		@Override
		public boolean check(Object value) {
			if(value.getClass() == String.class) {
				return ((String) value).length() > 7;
			} else return false;
		}

		@Override
		public Object parse(String value) {
			return value;
		}
	},
	rconPort("rcon port", 0) {
		@Override
		public boolean check(Object value) {
			return Keys.appPort.check(value);
		}

		@Override
		public Object parse(String value) throws InvalidKeysValueException {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				throw new InvalidKeysValueException(value, this);
			}
		}
	},
	startPassword("server start password", Configuration.none) {
		@Override
		public boolean check(Object value) {
			return Keys.rconPassword.check(value);
		}

		@Override
		public Object parse(String value) {
			return value;
		}
	},
	stopPassword("server stop password", Configuration.none) {
		@Override
		public boolean check(Object value) {
			return Keys.rconPassword.check(value);
		}

		@Override
		public Object parse(String value) {
			return value;
		}
	},
	timeout("time in second before killing the server", 60) {
		@Override
		public boolean check(Object value) {
			if(Number.class.isInstance(value)) {
				//verifier que c'est un nombre entier en utilisant le type double
				Double doubleValue = new BigDecimal(value + "").doubleValue();
				if(doubleValue >= 0 && doubleValue <= 15 * 60 && doubleValue % 1 == 0)
					return true;
			}
			return false;
		}

		@Override
		public Object parse(String value) throws InvalidKeysValueException {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				throw new InvalidKeysValueException(value, this);
			}
		}
	};

	private String key;
	private Object defaultValue;
	
	Keys(String key, Object defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}
	
	public String toString() {
		return key;
	}
	
	/** les valeurs sont supposes etre non nuls */
	public abstract boolean check(Object value);
	
	public abstract Object parse(String value) throws InvalidKeysValueException;
}
