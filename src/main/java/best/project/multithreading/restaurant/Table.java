package best.project.multithreading.restaurant;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Table extends Semaphore {

	private static final long serialVersionUID = -2224007226044882429L;
	private AtomicInteger cashBox;
	public Table(int permits) {
		super(permits);
		cashBox = new AtomicInteger(0);
	}
	public void payOff(int cashAmount) {
		cashBox.addAndGet(cashAmount);
		super.release();
	}
	public int getCashBox(){
		return cashBox.get();
	}
}
