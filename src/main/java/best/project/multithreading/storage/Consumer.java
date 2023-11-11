package best.project.multithreading.storage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName());

    private Storage storage;
    private AtomicInteger itemQuantity;
    private CountDownLatch latch;

    public Consumer(Storage storage, AtomicInteger itemQuantity, CountDownLatch latch) {
        this.storage = storage;
        this.itemQuantity = itemQuantity;
        this.latch = latch;
    }

    @Override
    public void run() {

        while (itemQuantity.get() > 0){
            try {
                synchronized (storage){
                    if(storage.getUploadStatus()){
                        storage.wait();
                    }
                    int uploadedValue = storage.getItem();
                    LOGGER.info(String.format("Consumed value is %d", uploadedValue));
                    storage.notify();
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, String.format("Exception for consumer wait()"), e);
            }
        }
        latch.countDown();
    }
}
