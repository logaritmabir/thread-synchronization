package os;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadMethods {
	private static Lock re = new ReentrantLock();
	private static Condition cond = re.newCondition();

	public static void makeReservation(WriterThread ThreadObj) throws InterruptedException {
		Shared.writerInterrupt++;
		re.lock();
		try {
			System.out.println("\n***************************************************************************\n");

			Random rand = new Random();
			int seatToReserve = rand.nextInt(5);

			Calendar calendar = Calendar.getInstance();

			System.out.println(ThreadObj.threadName + " is looking for available seats ---------------------------->"
					+ calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":"
					+ calendar.get(Calendar.SECOND) + ":" + calendar.get(Calendar.MILLISECOND));

			for (int i = 0; i < Shared.seats.length; i++) {
				System.out.print("Seat:" + i + " --> " + Shared.seats[i] + "\t");
			}
			System.out.println("\n" + ThreadObj.threadName + " is trying to reserve Seat" + seatToReserve);
			if (Shared.seats[seatToReserve] == 0) {
				Shared.seats[seatToReserve] = (int) ThreadObj.getId();
				System.out.println("Seat:" + seatToReserve + " has booked by " + ThreadObj.threadName + ".");
			} else {
				System.out.println("Seat:" + seatToReserve + " is already taken by someone else.");
			}
			Calendar calendar2 = Calendar.getInstance();

			System.out.println("\n" + ThreadObj.threadName + " finished looking for seats --------------------------->"
					+ calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar2.get(Calendar.MINUTE) + ":"
					+ calendar2.get(Calendar.SECOND) + ":" + calendar2.get(Calendar.MILLISECOND));

			System.out.println("\n***************************************************************************\n");

			ThreadObj.sleep(1000);
		} finally {
			Shared.writerInterrupt--;
			cond.signalAll();
			re.unlock();
		}
	}

	public static void queryReservation(ReaderThread ThreadObj) throws InterruptedException { // Reader
		re.lock();
		if (Shared.writerInterrupt != 0) {
			System.out.println(ThreadObj.threadName + " is waiting for a signal");
			cond.await();
		}

		try {
			System.out.println("\n***************************************************************************\n");

			Calendar calendar = Calendar.getInstance();

			System.out.println(ThreadObj.threadName + " is looking for available seats ---------------------------->"
					+ calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":"
					+ calendar.get(Calendar.SECOND) + ":" + calendar.get(Calendar.MILLISECOND));

			for (int i = 0; i < Shared.seats.length; i++) {
				System.out.print("Seat:" + i + " --> " + Shared.seats[i] + "\t");
			}

			Calendar calendar2 = Calendar.getInstance();

			System.out.println("\n" + ThreadObj.threadName + " finished looking for seats --------------------------->"
					+ calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar2.get(Calendar.MINUTE) + ":"
					+ calendar2.get(Calendar.SECOND) + ":" + calendar2.get(Calendar.MILLISECOND));

			System.out.println("\n***************************************************************************\n");

			ThreadObj.sleep(1000);
		} finally {
			re.unlock();
		}

	}
}
