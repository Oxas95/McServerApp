package main.process;

public class ThreadProcess extends Thread {
	private ProcessContainer apc;
	
	public ThreadProcess(ProcessContainer apc) {
		this.apc = apc;
	}
	
	@Override
	public void run() {
		if(apc.getProcess() != null) {
			try {
				apc.getProcess().waitFor();
				System.out.println("Process complete");
				apc.setProcess(null);
			} catch (InterruptedException e) {
				System.err.println("Process interrupted");
				apc.setProcess(null);
			}
		}
	}
}
