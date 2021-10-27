package main;

public enum Keys {
	startPassword("server start password"),
	stopPassword("server stop password"),
	appPort("app port"),
	batchPath("batch path"),
	rconPort("rcon port"),
	rconIp("rcon ip"),
	rconPassword("rcon password"),
	autoStart("start with auto-run server");

	private String key;
	
	Keys(String key) {
		this.key = key;
	}
	
	public String toString() {
		return key;
	}
}
