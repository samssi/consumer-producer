package fi.samssi;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private static List<String> messages = new ArrayList<String>();
    private boolean locked = false;

    public synchronized void addMessageToQueue(String message) {
        waitForIt();
        locked = true;
        messages.add(message);
        locked = false;
        notifyAll();
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

    public synchronized String popMessageFromQueue() {
        locked = true;
        if (messages.size() > 0) {
            String message = messages.get(0);
            messages.remove(0);
            return message;
        }
        locked = false;
        notifyAll();
        return "";
    }
}
