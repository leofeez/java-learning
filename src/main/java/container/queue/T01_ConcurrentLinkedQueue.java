package container.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class T01_ConcurrentLinkedQueue {

    static ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        concurrentLinkedQueue.add("");

        concurrentLinkedQueue.offer("");
    }
}
