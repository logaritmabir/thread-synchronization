package os;

public class ReaderThread extends Thread {
	public String threadName;
	public static int readerIndex;
	

	public ReaderThread() {
		this.threadName = "Reader" + ++readerIndex;
		
		Shared.systemThreads.put(this, (int)this.getId());
	}

	public void run() {
		try {
			ThreadMethods.queryReservation(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
