package fi.samssi.consumerproducer;

public class Consumer implements Runnable {

    private final String threadId;
    private final Queue queue;

    public Consumer(Queue queue, int threadId) {
        this.queue = queue;
        this.threadId = String.valueOf(threadId);
    }

    @Override
    public void run() {
        System.out.println("Consumer thread #" + threadId + " started!");
        while (ApplicationStarter.running) {
            consume();
        }
        System.out.println("Consumer thread #" + threadId + " ended!");
    }

    public void consume() {
        String message = queue.popMessageFromQueue();
        if (!"".equals(message)) {
            System.out.println("Thread # " + threadId + ": Consumed message from queue. Message was: " + message);
        }
    }

}
