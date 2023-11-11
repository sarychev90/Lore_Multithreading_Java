package best.project.multithreading.notification;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DelayedNotificationTest {

    private static final Logger LOGGER = Logger.getLogger(DelayedNotificationTest.class.getName());

    @Test
    void delayedNotificationTest() throws InterruptedException {

        BlockingQueue<DelayedNotification> queue = new DelayQueue<>();
        long startTime = System.currentTimeMillis();
        queue.put(DelayedNotification.builder().startTime(System.currentTimeMillis()+5000l).message("Message 1").build());
        queue.put(DelayedNotification.builder().startTime(System.currentTimeMillis()+15000l).message("Message 2").build());
        queue.put(DelayedNotification.builder().startTime(System.currentTimeMillis()+10000l).message("Message 3").build());

        while(!queue.isEmpty()){
            DelayedNotification notification = queue.take();
            LOGGER.info(notification.getMessage() + " time: "+ (System.currentTimeMillis() - startTime));
        }
        assertTrue(queue.isEmpty());
    }
}

