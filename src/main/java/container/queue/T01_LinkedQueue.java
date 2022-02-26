package container.queue;

import java.util.LinkedList;

public class T01_LinkedQueue {

    static LinkedList<String> concurrentLinkedQueue = new LinkedList<>();

    public static void main(String[] args) {
        concurrentLinkedQueue.offerFirst("a");
        concurrentLinkedQueue.offerFirst("b");

        String peekFirst = concurrentLinkedQueue.peekFirst();
        System.out.println(peekFirst);

        concurrentLinkedQueue.offerLast("c");
        concurrentLinkedQueue.offerLast("d");

        String peekLast = concurrentLinkedQueue.peekLast();
        System.out.println(peekLast);
    }
}
