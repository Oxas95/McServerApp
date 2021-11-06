package main.files;

public enum Keys {
	appPort("app port", 0),
	autoStart("starts by launching the server", false),
	batchPath("batch path", Configuration.none),
	rconIp("rcon ip", Configuration.none),
	rconPassword("rcon password", Configuration.none),
	rconPort("rcon port", 0),
	startPassword("server start password", Configuration.none),
	stopPassword("server stop password", Configuration.none),
	timeout("time in second before killing the server", 60);

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
	
	//TODO abstract function check(Object) verifie si l'objet correspond aux critères de la clé 
}
