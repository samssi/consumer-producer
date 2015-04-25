package fi.samssi.consumerproducer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Queue {
    private static List<String> messages = new ArrayList<>();
    private boolean locked = false;

    public synchronized void addMessageToQueue(String message) {
        doLockedOperation(() -> messages.add(message));
    }

    private synchronized <T> T doLockedOperation(Supplier<T> function) {
        waitForIt();
        locked = true;
        T result = function.get();
        locked = false;
        notifyAll();
        return result;
    }

    public synchronized void waitForIt() {
        while(locked) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized Supplier<String> messagePopper() {
        return () -> {
            if (messages.size() > 0) {
                String message = messages.get(0);
                messages.remove(0);
                return message;
            }
            return "";
        };
    }

    public synchronized String popMessageFromQueue() {
        return doLockedOperation(messagePopper());
    }
}
