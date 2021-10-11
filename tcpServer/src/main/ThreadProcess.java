package main;

public class ThreadProcess extends Thread {
	private AbstractProcessContainer apc;
	
	public ThreadProcess(AbstractProcessContainer apc) {
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
			}
		}
	}
}
