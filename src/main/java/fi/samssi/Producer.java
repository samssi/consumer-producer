package fi.samssi;

public class Producer implements Runnable {
    private final Queue queue;
    private final String threadId;

    public Producer(Queue queue, int threadId) {
        this.queue = queue;
        this.threadId = String.valueOf(threadId);
    }

    @Override
    public void run() {
        System.out.println("Producer thread #" + threadId + " started!");
        while(ApplicationStarter.running) {
            int randomAmount = (int) Math.round(Math.random() * 1000);
            writeMessagesToQueue(randomAmount);
            sleep(15000);
        }
        System.out.println("Producer thread #" + threadId + " ended!");
    }

    private void sleep(int timeInMilliseconds) {
        try {
            Thread.sleep(timeInMilliseconds);
        } catch (InterruptedException e) { }
    }

    private void writeMessagesToQueue(int amount) {
        System.out.println("Producing: " + amount + " messages.");
        for (int i = 0; i < amount; i++) {
            queue.addMessageToQueue(generateString());
        }
    }

    private String generateString() {
        return String.valueOf((int) Math.round(Math.random() * 10000));
    }

}
