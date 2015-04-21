package fi.samssi;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private static List<String> messages = new ArrayList<String>();

    public synchronized void addMessageToQueue(String message) {
        messages.add(message);
    }

    public synchronized String popMessageFromQueue() {
        if (messages.size() > 0) {
            String message = messages.get(0);
            messages.remove(0);
            return message;
        }
        return "";
    }
}
