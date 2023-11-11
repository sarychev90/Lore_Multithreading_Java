package best.project.multithreading.storage;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Producer.class.getName());

    private Storage storage;
    private AtomicInteger itemQuantity;

    private CountDownLatch latch;

    public Producer(Storage storage, AtomicInteger itemQuantity, CountDownLatch latch) {
        this.storage = storage;
        this.itemQuantity = itemQuantity;
        this.latch = latch;
    }

    @Override
    public void run() {
        while(itemQuantity.decrementAndGet() > 0) {
            try {
                synchronized (storage){
                    if(!storage.getUploadStatus()){
                        storage.wait();
                    }
                    int generatedValue = new Random().nextInt(100);
                    LOGGER.info(String.format("Produced value is %d", generatedValue));
                    storage.setItem(generatedValue);
                    storage.notify();
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, String.format("Exception for producer wait()"), e);
            }
        }
        latch.countDown();
    }
}
