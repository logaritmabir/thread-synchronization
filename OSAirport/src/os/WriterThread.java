package os;

public class WriterThread extends Thread {
	public String threadName;
	private static int writerIndex = 0;
	
	public WriterThread() {
		this.threadName = "Writer" + ++writerIndex;
		Shared.systemThreads.put(this, (int)this.getId());
	}

	public void run() {
		try {
			ThreadMethods.makeReservation(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
