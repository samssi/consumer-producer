package fi.samssi.consumerproducer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Queue {
    private static List<String> messages = new ArrayList<>();

    public synchronized void addMessageToQueue(String message) {
        messages.add(message);
        notifyAll();
    }

    private synchronized <T> T doLockedOperation(Supplier<T> function) {
        waitForIt();
        T result = function.get();
        return result;
    }

    private boolean empty() {
        return messages.size() == 0;
    }

    public synchronized void waitForIt() {
        while(empty()) {
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
