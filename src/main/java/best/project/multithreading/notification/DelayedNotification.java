package best.project.multithreading.notification;

import lombok.Builder;
import lombok.Getter;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Builder(toBuilder = true)
@Getter
public class DelayedNotification implements Delayed {

    private String message;
    private long startTime;

    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    public int compareTo(Delayed o) {
        return (int) (this.startTime - ((DelayedNotification) o).startTime);
    }
}
