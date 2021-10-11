package main;

public abstract class AbstractProcessContainer {
	protected Process process;
	
	public AbstractProcessContainer() {
		process = null;
	}

	public Process getProcess() {
		return process;
	}

	public AbstractProcessContainer setProcess(Process process) {
		this.process = process;
		return this;
	}
}
