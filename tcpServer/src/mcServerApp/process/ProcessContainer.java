package mcServerApp.process;

public  class ProcessContainer {
	protected Process process;
	
	public ProcessContainer() {
		process = null;
	}

	public Process getProcess() {
		return process;
	}

	public ProcessContainer setProcess(Process process) {
		this.process = process;
		return this;
	}
}
