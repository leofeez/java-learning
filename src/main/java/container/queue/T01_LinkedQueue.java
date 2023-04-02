package container.queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class T01_LinkedQueue {

    static LinkedList<String> concurrentLinkedQueue = new LinkedList<>();

    public static void main(String[] args) {
        concurrentLinkedQueue.offerFirst("a");
        concurrentLinkedQueue.offerFirst("b");
        concurrentLinkedQueue.offerFirst("c");
        concurrentLinkedQueue.offerFirst("d");

//        String peekFirst = concurrentLinkedQueue.peekFirst();
//        System.out.println(peekFirst);
//
//        concurrentLinkedQueue.offerLast("e");
//        concurrentLinkedQueue.offerLast("f");

        System.out.println(concurrentLinkedQueue);


        List<String> list = new ArrayList<>();
        list.removeIf(e -> e.equals(""));

        concurrentLinkedQueue.removeIf(s -> s.equals("c"));
//        for (String s : concurrentLinkedQueue) {
//            if (s.equals("c")) concurrentLinkedQueue.pop();
//        }

        String peekLast = concurrentLinkedQueue.peekLast();
        System.out.println(peekLast);
    }
}
