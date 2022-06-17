package os;

import java.util.HashMap;

public class Airport{

	
	public static void main(String[] args) throws InterruptedException {
		createWriters(3);
		createReaders(10);
		System.out.println(Shared.systemThreads.keySet() +" "+Shared.systemThreads.values());
		for (Thread ThreadObj : Shared.systemThreads.keySet()) {
			ThreadObj.join();
		}
		System.out.println("Done");
	}
	
	public static void createReaders(int quantity) throws InterruptedException{
		for(int i=0;i<quantity;i++) {
			ReaderThread RTobj = new ReaderThread();
			RTobj.start();
		}
	}
	public static void createWriters(int quantity) throws InterruptedException{
		for(int i=0;i<quantity;i++) {
			WriterThread WRobj = new WriterThread();
			WRobj.start();
		}
	}
}
class Shared{
	public static int seats[] = new int[5];
	public static int writerInterrupt = 0;
	public static HashMap<Thread,Integer> systemThreads = new HashMap<Thread, Integer>();
}
