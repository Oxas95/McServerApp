package main.files;

import java.io.File;

import org.json.JSONObject;

public enum Keys {
	appPort("app port", 0) {
		@Override
		public boolean check(JSONObject obj) {
			Object value = obj.get(this.toString());
			if(Number.class.isInstance(value)) {
				//verifier que c'est un nombre entier en utilisant le type double
				Double intValue = (Double) value;
				if(intValue > 1000 && intValue < 65535)
					return true;
			}
			return false;
		}
	},
	autoStart("starts by launching the server", false) {
		@Override
		public boolean check(JSONObject obj) {
			Object value = obj.get(this.toString());
			if(value.toString() == Boolean.TRUE.toString() || value.toString() == Boolean.FALSE.toString()) {
				if(value.getClass() == Boolean.class || value.getClass() == boolean.class)
					return true;
			}
			return false;
		}
	},
	batchPath("batch path", Configuration.none) {
		@Override
		public boolean check(JSONObject obj) {
			Object value = obj.get(this.toString());
			if(value.getClass() == String.class) {
				value = ((String) value).replace("/", File.separator);
				obj.put(this.toString(), value);
				File f = new File((String) value);
				return f.exists();
			} else return false;
		}
	},
	rconIp("rcon ip", Configuration.none) {
		@Override
		public boolean check(JSONObject obj) {
			Object value = obj.get(this.toString());
			if(value.getClass() == String.class) {
				return !((String) value).isEmpty();
			} else return false;
		}
	},
	rconPassword("rcon password", Configuration.none) {
		@Override
		public boolean check(JSONObject obj) {
			Object value = obj.get(this.toString());
			if(value.getClass() == String.class) {
				return ((String) value).length() > 7;
			} else return false;
		}
	},
	rconPort("rcon port", 0) {
		@Override
		public boolean check(JSONObject obj) {
			Object value = obj.get(this.toString());
			if(Number.class.isInstance(value)) {
				//verifier que c'est un nombre entier en utilisant le type double
				Double intValue = (Double) value;
				if(intValue > 1000 && intValue < 65535)
					return true;
			}
			return false;
		}
	},
	startPassword("server start password", Configuration.none) {
		@Override
		public boolean check(JSONObject obj) {
			Object value = obj.get(this.toString());
			if(value.getClass() == String.class) {
				return ((String) value).length() > 7;
			} else return false;
		}
	},
	stopPassword("server stop password", Configuration.none) {
		@Override
		public boolean check(JSONObject obj) {
			Object value = obj.get(this.toString());
			if(value.getClass() == String.class) {
				return ((String) value).length() > 7;
			} else return false;
		}
	},
	timeout("time in second before killing the server", 60) {
		@Override
		public boolean check(JSONObject obj) {
			Object value = obj.get(this.toString());
			if(Number.class.isInstance(value)) {
				//verifier que c'est un nombre entier en utilisant le type double
				Double intValue = (Double) value;
				if(intValue > -1 && intValue <= 15 * 60)
					return true;
			}
			return false;
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
	public abstract boolean check(JSONObject obj);
}
