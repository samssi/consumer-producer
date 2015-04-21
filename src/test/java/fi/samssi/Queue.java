package fi.samssi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Queue {
    private static List<String> messages = new ArrayList<String>();
    private boolean locked = false;

    public synchronized void addMessageToQueue(String message) {
        doLockedOperationForBoolean(() -> messages.add(message));
    }

    private synchronized void doLockedOperationForBoolean(Supplier<Boolean> function) {
        waitForIt();
        locked = true;
        function.get();
        locked = false;
        notifyAll();
    }

    private synchronized String doLockedOperationForString(Supplier<String> function) {
        waitForIt();
        locked = true;
        String result = function.get();
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
        return doLockedOperationForString(messagePopper());
    }
}
