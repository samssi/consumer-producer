package fi.samssi.consumerproducer;

public class ApplicationStarter {

    public static boolean running = true;
    public static Queue queue = new Queue();

    public static void main(String... args) {
        startProducer();
        startConsumers(7);
    }

    public static void startProducer() {
        new Thread(new Producer(queue, 1)).start();
    }

    public static void startConsumers(int amount) {
        for (int i = 0; i < amount; i++) {
            new Thread(new Consumer(queue, i+1)).start();
        }

    }
}
